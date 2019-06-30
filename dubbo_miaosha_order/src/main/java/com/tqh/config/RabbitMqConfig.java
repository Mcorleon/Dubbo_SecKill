package com.tqh.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author Mcorleon
 * @Date 2019/2/26 21:19
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 声明rabbitTemplate模板
     * 必须是prototype类型，因为不同的sender需要支持不一样的ConfirmCallback回调函数
     */
    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    /**
     * 声明一个队列 支持持久化.
     */
    @Bean
    public Queue directQueue() {
        Map<String, Object> args = new HashMap<>();
        // 设置该Queue的死信的信箱
        args.put("x-dead-letter-exchange", "DEAD_LETTER_EXCHANGE");
        // 设置死信routingKey
        args.put("x-dead-letter-routing-key", "DEAD_ROUTING_KEY");
        return QueueBuilder.durable("DIRECT_QUEUE").withArguments(args).build();
    }

    /**
     * 声明Direct交换机 支持持久化.
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("DIRECT_EXCHANGE", true, false);
    }

    /**
     * 通过绑定键 将指定队列绑定到一个指定的交换机 .
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("DIRECT_ROUTING_KEY");
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue("DEAD_LETTER_QUEUE");
    }

    @Bean
    public Exchange deadLetterExchange() {
        return new DirectExchange("DEAD_LETTER_EXCHANGE", true, false);
    }

    @Bean
    public Binding deadLetterBindding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with("DEAD_ROUTING_KEY").noargs();
    }


}
