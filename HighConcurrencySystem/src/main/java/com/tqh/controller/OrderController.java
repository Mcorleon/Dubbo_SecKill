package com.tqh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tqh.model.Result;
import com.tqh.api.OrderService;
import com.tqh.util.JsonTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author Mcorleon
 * @Date 2019/2/22 21:20
 */

@RequestMapping("/order")
@RestController
@Api(tags = "订单模块")
public class OrderController {
    @Reference(version = "1.0.0")
    OrderService orderService;

    /**
     *QPS: 447
     * 1000*10
     * rabbitMQ+接口优化后600
     */
    @PostMapping("/generateOrder")
    @ApiOperation(value = "生成订单", notes = "过程：redis预减库存->生成订单->交给mq")
//    @AccessLimit(limit = 500,sec = 5)
    public Result generateOrder(String nickName, String miaosha_id, int goods_num, String address_id) throws JsonProcessingException {
        return orderService.generateOrder(miaosha_id,nickName,goods_num,address_id);
    }

    /**
     *QPS: 285
     * 500*20
     */
    @PostMapping("/getOrderVoByUid")
    @ApiOperation(value = "获取订单列表视图" )
    public String getOrderVoByUid(String uid,int page, int limit){

        Map<String, Object> map = orderService.getOrderVoByUid(uid,page, limit);
        return JsonTool.objectToJson(map);
    }



}
