package com.tqh.api;

import com.tqh.model.MiaoshaGoodsVo;

import java.util.Map;

/**
 * @Author Mcorleon
 * @Date 2019/6/29 15:30
 */
public interface GoodsService {
    Map<String, Object> getGoodsList(int page, int limit);

    String getMiaoshaGoodsList(String goods_id,int page, int limit);

    MiaoshaGoodsVo getMiaoshaGoodByID(String id);

    boolean decreaseStock(String goods_id);

    void addAllStockToRedis();


}
