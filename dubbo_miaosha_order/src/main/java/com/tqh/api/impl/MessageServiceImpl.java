package com.tqh.api.impl;

import com.tqh.mapper.MessageMapper;
import com.tqh.model.OrderMessage;
import com.tqh.api.MessageService;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author Mcorleon
 * @Date 2019/4/16 10:06
 */

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void addOrderMessage(CorrelationData correlationId, String exchange, String routing_key, String message) {
        OrderMessage orderMessage=new OrderMessage();
        orderMessage.setId(correlationId.getId());
        orderMessage.setExchange_name(exchange);
        orderMessage.setRouting_key(routing_key);
        orderMessage.setMessage_body(message);
        orderMessage.setRetry_count(0L);
        orderMessage.setMessage_time(new Date());
        orderMessage.setState(0L);
        messageMapper.addOrderMessage(orderMessage);
    }

    @Override
    public void deleteOrderMessageById(String id) {
        messageMapper.deleteOrderMessageById(id);
    }

    @Override
    public void updateOrderMessageStateById(String id, int state) {
        messageMapper.updateOrderMessageStateById(id, state);
    }

    @Override
    public OrderMessage getOrderMessageById(String id) {
        return messageMapper.getOrderMessageById(id);
    }

    @Override
    public void increaseOrderMessageRetryCountById(String id) {
        messageMapper.increaseOrderMessageRetryCountById(id);
    }


}
