package com.zhp.taotaole.component;

import com.zhp.taotaole.entity.Order;
import com.zhp.taotaole.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName: OrderStatusListener
 * Package: com.zhp.taotaole.component
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/9 13:48
 * @Version 1.0
 */
@Component
public class OrderStatusListener {

    private final OrderRepository orderRepository;

    public OrderStatusListener(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }


    //@RabbitListener 注解来监听指定的 order.queue 队列中的消息。一旦有新的订单消息到达，消费者将异步处理订单的状态更新逻辑
    @RabbitListener(queues = "order.queue")
    public void handleOrderMessage(Order order) {
        try {
            System.out.println("Processing order:" + order.getOrderId());

            // 更新订单状态
            //order.setStatus(Order.Status.completed);

            // 持久化订单状态
            //orderRepository.save(order);

            System.out.println("Processed order:" + order.getOrderId());
        } catch (Exception e) {
            System.err.println("Failed to process order: " + order.getOrderId());
            e.printStackTrace();
            // 根据情况决定是否重试或记录异常
        }
    }

}

