package com.zhp.taotaole.service;

import com.zhp.taotaole.entity.OrderMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * ClassName: OrderProducer
 * Package: com.zhp.taotaole.service
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/9 15:26
 * @Version 1.0
 */
@Service
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;
    public  OrderProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }
    public void sendOrderMessage(OrderMessage orderMessage){
        rabbitTemplate.convertAndSend("order.exchange","order.routingKey",orderMessage);
    }
}
