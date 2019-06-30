package com.tqh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tqh.model.MiaoshaGoodsVo;
import com.tqh.api.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Mcorleon
 * @Date 2019/2/21 20:15
 */
@Api(tags = "商品模块")
@RestController
@RequestMapping("/goods")
public class GoodsController{

    @Reference(version = "1.0.0")
    GoodsService goodsService;

    @GetMapping("/getGoodsList")
    @ApiOperation(value = "获取商品列表", notes = "分页获取商品列表，配合前端layui使用")
    public Map getGoodsList(int page,int limit){

        Map<String, Object> map = goodsService.getGoodsList(page, limit);
        return map;
    }
    @PostMapping("/getMiaoshaGoodsList/{goods_id}")
    @ApiOperation(value = "获取秒杀商品列表", notes = "分页获取秒杀商品列表，配合前端layui使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true),
            @ApiImplicitParam(name = "goods_id", value = "商品id", required = true)
    })
    public String getMiaoshaGoodsList( @PathVariable String goods_id,int page, int limit){

        return   goodsService.getMiaoshaGoodsList(goods_id, page,limit);

    }
    @ApiOperation(value = "获取秒杀商品", notes = "根据id获取秒杀商品")
    @PostMapping("/getMiaoshaGoodByID")
    public MiaoshaGoodsVo getMiaoshaGoodByID(String id){
        return   goodsService.getMiaoshaGoodByID(id);

    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("将所有库存加入redis缓存");
//        goodsService.addAllStockToRedis();
//    }
}
