package com.tqh.api;

import com.tqh.model.MiaoshaGoodsVo;

import java.util.Map;

/**
 * @Author Mcorleon
 * @Date 2019/2/21 20:16
 */
public interface GoodsService {
    Map<String, Object> getGoodsList(int page, int limit);

    String getMiaoshaGoodsList(String goods_id, int page, int limit);

    MiaoshaGoodsVo getMiaoshaGoodByID(String id);

    boolean decreaseStock(String goods_id);

    void addAllStockToRedis();
}
