//package com.tqh.api;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.rabbitmq.client.Channel;
//
//import com.tqh.model.Order;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//
///**
// * @Author Mcorleon
// * @Date 2019/2/26 21:44
// */
//@Service
//public class MQReciver {
//    private static final Logger logger = LoggerFactory.getLogger(MQReciver.class);
//    @Reference
//    private GoodsService goodsService;
//
//    @Reference
//    private OrderService orderService;
//
//
//    /**
//     *  mysql减库存 采用手动应答模式, 手动确认应答更为安全稳定。消费者默认是单线程串行消费
//     */
//    @RabbitListener(queues = "DIRECT_QUEUE")
//    public void dealOrder(Message message, Channel channel) throws IOException{
//        String order_id=new String(message.getBody());
//        try {
//            Order order=orderService.getOrderById(order_id);
//            goodsService.decreaseStock(order.getGoods_id());
//            logger.info("消费完毕,mysql库存已扣减");
//        } catch (Exception e) {
//            logger.error("消费者异常",e);
//            //丢弃异常的消息，会进入死信队列
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//        }
//        //false只确认当前一个消息收到，true确认所有consumer获得的消息
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    }
//
//    /**
//     *死信队列消费者
//     */
//    @RabbitListener(queues = "DEAD_LETTER_QUEUE")
//    public void dealDeadMessage(Message message, Channel channel) throws IOException{
//        //这里收到消费失败的消息，可再次尝试消费，失败了就记录日志完丢弃
//        logger.info("死信队列消息:"+new String(message.getBody()));
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    }
//
//
//}
