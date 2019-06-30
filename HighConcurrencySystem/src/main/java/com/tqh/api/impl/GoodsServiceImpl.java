//package com.tqh.service.impl;
//
//import com.github.pagehelper.Page;
//import com.tqh.mapper.GoodsMapper;
//import com.tqh.model.GoodIDAndStock;
//import com.tqh.model.Goods;
//import com.tqh.model.MiaoshaGoodsVo;
//import com.tqh.model.Order;
//import com.tqh.api.GoodsService;
//import com.tqh.util.JsonTool;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author Mcorleon
// * @Date 2019/2/21 20:16
// */
//@Service
//@CacheConfig(cacheNames = "goods")
//public class GoodsServiceImpl implements GoodsService {
//    @Autowired
//    GoodsMapper goodsMapper;
//
//    @Autowired
//    @Lazy
//    OrderServiceImpl orderService;
//
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//    @Override
//    public Map<String, Object> getGoodsList() {
//        List<Goods> list=goodsMapper.getGoodsList();
//        long total = ((Page<Goods>) list).getTotal();
//        Map<String, Object> map = new HashMap();
//        map.put("code", 0);
//        map.put("msg", "getGoodsList");
//        map.put("count", total);
//        map.put("data", list);
//        return map;
//    }
//
//    @Override
//    public String getMiaoshaGoodsList(String goods_id) {
//        List<MiaoshaGoodsVo> list=goodsMapper.getMiaoshaGoodsList(goods_id);
//        long total = ((Page<MiaoshaGoodsVo>) list).getTotal();
//        Map<String, Object> map = new HashMap();
//        map.put("code", 0);
//        map.put("msg", "getMiaoshaGoodsList");
//        map.put("count", total);
//        map.put("data", list);
//        return JsonTool.objectToJson(map);
//    }
//
//    @Override
//    public MiaoshaGoodsVo getMiaoshaGoodByID(String id) {
//        return goodsMapper.getMiaoshaGoodByID(id);
//    }
//
//    @Override
//    @Transactional
//    public boolean decreaseStock(String goods_id) {
//        return goodsMapper.decreaseStock(goods_id);
//    }
//
//    @Override
//    @Transactional
//    public void addAllStockToRedis() {
//        List<GoodIDAndStock> list=goodsMapper.getGoodsIDAndStock();
//        for(GoodIDAndStock goodIDAndStock:list){
//            stringRedisTemplate.opsForValue().set(goodIDAndStock.getId()+"stock",String.valueOf(goodIDAndStock.getStock()));
//        }
//    }
//
//    @Async("taskExecutor")
//    public void doTaskOne() {
//        System.out.println("开始做任务一");
//        long start = System.currentTimeMillis();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
//    }
//
//    @Async("taskExecutor")
//    public void doTaskTwo(String order_id) {
//        Order order=orderService.getOrderById(order_id);
//        decreaseStock(order.getGoods_id());
//        System.out.println("消费完毕,mysql库存已扣减");
//    }
//}
