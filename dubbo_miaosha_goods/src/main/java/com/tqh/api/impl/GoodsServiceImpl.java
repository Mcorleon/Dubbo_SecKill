package com.tqh.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tqh.mapper.GoodsMapper;
import com.tqh.model.GoodIDAndStock;
import com.tqh.model.Goods;
import com.tqh.model.MiaoshaGoodsVo;
import com.tqh.api.GoodsService;
import com.tqh.util.JsonTool;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Mcorleon
 * @Date 2019/6/29 15:30
 */
@Service(version = "1.0.0")
public class GoodsServiceImpl implements GoodsService ,InitializingBean{

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Map<String, Object> getGoodsList(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Goods> list=goodsMapper.getGoodsList();
        long total = ((Page<Goods>) list).getTotal();
        Map<String, Object> map = new HashMap();
        map.put("code", 0);
        map.put("msg", "getGoodsList");
        map.put("count", total);
        map.put("data", list);
        return map;
    }

    @Override
    public String getMiaoshaGoodsList(String goods_id,int page, int limit) {
        PageHelper.startPage(page, limit);
        List<MiaoshaGoodsVo> list=goodsMapper.getMiaoshaGoodsList(goods_id);
        long total = ((Page<MiaoshaGoodsVo>) list).getTotal();
        Map<String, Object> map = new HashMap();
        map.put("code", 0);
        map.put("msg", "getMiaoshaGoodsList");
        map.put("count", total);
        map.put("data", list);
        return JsonTool.objectToJson(map);
    }

    @Override
    public MiaoshaGoodsVo getMiaoshaGoodByID(String id) {
        return goodsMapper.getMiaoshaGoodByID(id);
    }

    @Override
    public boolean decreaseStock(String goods_id) {
        return goodsMapper.decreaseStock(goods_id);
    }

    @Override
    public void addAllStockToRedis() {
        System.out.println("redis库存预载入");
        List<GoodIDAndStock> list=goodsMapper.getGoodsIDAndStock();
        for(GoodIDAndStock goodIDAndStock:list){
            stringRedisTemplate.opsForValue().set(goodIDAndStock.getId()+"stock",String.valueOf(goodIDAndStock.getStock()));
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        addAllStockToRedis();
    }
}
