package com.tqh.api.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tqh.api.GoodsService;
import com.tqh.api.MQSender;
import com.tqh.api.OrderService;
import com.tqh.api.UserSerive;
import com.tqh.mapper.OrderMapper;
import com.tqh.model.*;
import com.tqh.util.RedisTool;
import com.tqh.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Mcorleon
 * @Date 2019/2/23 14:27
 */
@Service(version = "1.0.0")
@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Reference(version = "1.0.0")
    UserSerive userService;

    @Reference(version = "1.0.0")
    GoodsService goodsService;

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    MQSender sender;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTool redisTool;
    private static final SnowFlake SNOW_FLAKE = new SnowFlake(1, 1);

    @Override
    public Result generateOrder(String miaosha_id, String nickName, int goods_num, String address_id) {
        //执行lua脚本保证原子性(查库存和减库存的间隙不会有其他线程查到未减的库存)，返回扣减前的库存
        Long stock = redisTool.decreaseStock(miaosha_id + "stock", String.valueOf(goods_num));
        if (stock == null || stock == -1) {
            logger.info("商品不存在");
            return Result.UNKONWN_GOODS_ERR;
        }
        if (stock - goods_num >= 0) {
            Result result = null;
            try {
                //下单
                result = dealOrderMessage(miaosha_id, nickName, goods_num, address_id);
                if (200 == result.getCode()) {
                    //订单生成成功(未支付)
                    //交给队列 异步减mysql库存或者发回扣等等...(严格来说应该在支付后进行,此处省略)
                    sender.sendOrderMessage(result.getDetail());
//                goodsService.doTaskOne();
//                goodsService.doTaskTwo(result.getDetail());

                } else {
                    //生成订单失败,把减掉的redis库存加回去
                    stringRedisTemplate.opsForValue().increment(miaosha_id + "stock", goods_num);
                }
            } catch (Exception e) {
                e.printStackTrace();
                stringRedisTemplate.opsForValue().increment(miaosha_id + "stock", goods_num);
            }

            return result;
        } else {
            //没库存了
            logger.info("没有库存了");
            return Result.LACK_STOCK_ERR;
        }

    }

    @Override
    public Map<String, Object> getOrderVoByUid(String uid, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<OrderVo> list = orderMapper.getOrderVoByUid(uid);
        for (OrderVo orderVo : list) {
            String stateVo;
            switch (orderVo.getState()) {
                case 0:
                    stateVo = "未付款";
                    break;
                case 1:
                    stateVo = "待发货";
                    break;
                case 2:
                    stateVo = "已发货";
                    break;
                case 3:
                    stateVo = "已收货";
                    break;
                case 4:
                    stateVo = "已取消";
                    break;
                case 5:
                    stateVo = "已完成";
                    break;
                default:
                    stateVo = "状态有误";
            }
            orderVo.setStateVo(stateVo);
        }
        long total = ((Page<OrderVo>) list).getTotal();
        Map<String, Object> map = new HashMap();
        map.put("code", 0);
        map.put("msg", "getOrderVoByUid");
        map.put("count", total);
        map.put("data", list);
        return map;
    }

    @Override
    public Result dealOrderMessage(String miaosha_id, String nickName, int goods_num, String address_id) {

        //找出用户
        User user = userService.findUserByName(nickName);
        if (user == null) {
            logger.info(Result.UNKONWN_USER_ERR.toString());
            return Result.UNKONWN_USER_ERR;
        }
        //找出秒杀商品
        MiaoshaGoodsVo miaoshaGoodsVo = goodsService.getMiaoshaGoodByID(miaosha_id);
        if (miaoshaGoodsVo == null) {
            logger.info(Result.UNKONWN_GOODS_ERR.toString());
            return Result.UNKONWN_GOODS_ERR;
        }
        if (miaoshaGoodsVo.getStock() < 1) {
            logger.info(Result.LACK_STOCK_ERR.toString());
            return Result.LACK_STOCK_ERR;
        }
        long current_time = System.currentTimeMillis();
        if (current_time < miaoshaGoodsVo.getStart_time().getTime()) {
            logger.info(Result.BEFORE_MIAOSHA_ERR.toString());
            return Result.BEFORE_MIAOSHA_ERR;
        }
        if (current_time > miaoshaGoodsVo.getEnd_time().getTime()) {
            logger.info(Result.END_MIAOSHA_ERR.toString());
            return Result.END_MIAOSHA_ERR;
        }
        //找出地址
        Address address = userService.getAddressByAddressID(address_id);
        if (address == null) {
            logger.info(Result.UNKONWN_ADDRESS_ERR.toString());
            return Result.UNKONWN_ADDRESS_ERR;
        }

        //生成订单
        Order order = new Order();
        order.setId(String.valueOf(SNOW_FLAKE.nextId()));
        order.setUser_id(String.valueOf(user.getId()));
        order.setGoods_id(miaoshaGoodsVo.getGoods_id());
        order.setAddress_id(address.getId());
        order.setGoods_name(miaoshaGoodsVo.getName());
        order.setGoods_num((long) goods_num);
        order.setGood_price(miaoshaGoodsVo.getMiaosha_price());
        order.setState(0);
        order.setOrder_time(new Date());
        orderMapper.inserOrder(order);
        logger.info("下单成功!");
        return new Result(200, "下单成功", order.getId());

    }

    @Override
    public Order getOrderById(String order_id) {
        return orderMapper.getOrderById(order_id);
    }


}
