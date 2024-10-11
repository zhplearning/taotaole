package com.zhp.taotaole.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * ClassName: RabbitMQConfig
 * Package: com.zhp.taotaole.configuration
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/9 13:56
 * @Version 1.0
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter()); // 使用 JSON 转换器
        return factory;
    }
    @Bean
    public Queue orderQueue(){
        return new Queue("order.queue",true);    //durable=true表示队列持久化
    }

    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange("order.exchange");
    }

    @Bean
    public Binding binding(Queue orderQueue,DirectExchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order.routing.key");
    }
}
