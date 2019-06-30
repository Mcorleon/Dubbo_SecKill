package com.tqh.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author Mcorleon
 * @Date 2019/2/26 21:47
 */
@Service
public class MQSender implements RabbitTemplate.ReturnCallback {

    private RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MQSender.class);
    @Autowired
    private MessageService messageService;

    /**
     * 构造方法注入
     */
    @Autowired
    public MQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //没有绑定的队列，退回（到达exchange了但是找不到routingKey）
        rabbitTemplate.setReturnCallback(this);
        //没有到达交换机，或者不存在该交换机，退回
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                logger.error("订单消息发送失败" + cause + correlationData.toString());
                messageService.updateOrderMessageStateById(correlationData.getId(), -1);
                logger.error("异常订单消息已记录,请查看数据库");

            } else {
                logger.info("订单消息发送成功,消息id:" + correlationData.getId());
                //发送成功，待消费
                messageService.updateOrderMessageStateById(correlationData.getId(), 1);
            }
        });
    }

    public void sendOrderMessage(String order_id) {
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", order_id, correlationId);
        messageService.addOrderMessage(correlationId, "DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", order_id);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        logger.info("sender return success" + message.toString() + "===" + i + "===" + s1 + "===" + s2);
    }

}
