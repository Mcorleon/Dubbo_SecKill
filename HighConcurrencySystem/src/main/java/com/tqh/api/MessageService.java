package com.tqh.api;

import com.tqh.model.OrderMessage;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @Author Mcorleon
 * @Date 2019/4/16 10:05
 */
public interface MessageService {
    void addOrderMessage(CorrelationData correlationId,String exchange, String routing_key, String message);

    void deleteOrderMessageById(String id);

    void updateOrderMessageStateById(String id, int state);

    OrderMessage getOrderMessageById(String id);


    void increaseOrderMessageRetryCountById(String id);
}
