package com.tqh.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tqh.model.Order;
import com.tqh.model.Result;

import java.util.Map;

/**
 * @Author Mcorleon
 * @Date 2019/2/23 14:27
 */
public interface OrderService {
     Result generateOrder(String miaosha_id, String nickName, int goods_num, String address_id) throws JsonProcessingException;

    Map<String, Object> getOrderVoByUid(String uid,int page,int limit);

   Result dealOrderMessage(String miaosha_id, String nickName, int goods_num, String address_id) throws Exception;

    Order getOrderById(String order_id);
}
