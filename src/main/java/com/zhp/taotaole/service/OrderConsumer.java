package com.zhp.taotaole.service;

import com.zhp.taotaole.entity.OrderMessage;
import com.zhp.taotaole.entity.Product;
import com.zhp.taotaole.repository.OrderRepository;
import com.zhp.taotaole.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * ClassName: OrdetConsumer
 * Package: com.zhp.taotaole.service
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/9 16:43
 * @Version 1.0
 */
@Component
public class OrderConsumer {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderConsumer(ProductRepository productRepository,OrderRepository orderRepository){
        this.productRepository=productRepository;
        this.orderRepository=orderRepository;
    }
    @Transactional
    @RabbitListener(queues = "order.queue")
    public void handleOrderMessage(OrderMessage orderMessage){
        System.out.println("Received message: "+orderMessage);
        if("stock_update".equals(orderMessage.getMessageType())){
            //处理库存更新逻辑
            Product product=productRepository.findById(orderMessage.getProductId())
                    .orElseThrow(()->new EntityNotFoundException("商品不存在"));
            System.out.println("orderMessage.getProductId():"+orderMessage.getProductId());
            product.setStock(product.getStock()-orderMessage.getQuantity());
            System.out.println("product stock:"+product.getStock());
            productRepository.save(product);
        }
        //其它消息类型的处理（如支付成功）
    }
}
