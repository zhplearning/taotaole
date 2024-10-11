package com.zhp.taotaole.service;

import com.zhp.taotaole.entity.Order;
import com.zhp.taotaole.entity.OrderMessage;
import com.zhp.taotaole.entity.Product;
import com.zhp.taotaole.entity.User;
import com.zhp.taotaole.repository.OrderRepository;
import com.zhp.taotaole.repository.ProductRepository;
import com.zhp.taotaole.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ClassName: OrderService
 * Package: com.zhp.taotaole.service
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/8 22:28
 * @Version 1.0
 */
@Service
public class OrderService {

    //定义logger对象
    private static final Logger logger=LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    //private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    private final OrderProducer orderProducer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository,ProductRepository productRepository,UserRepository userRepository,OrderProducer orderProducer){
        this.orderRepository=orderRepository;
        this.productRepository=productRepository;
        this.userRepository=userRepository;
        this.orderProducer=orderProducer;
    }

    @Transactional
    public Order createOrder(Long buyerId,Long productId,int quantity) {
        logger.debug("Received request to create order with buyerId={}, productId={}, quantity={}", buyerId, productId, quantity);
        // 查询买家
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new EntityNotFoundException("买家不存在！"));
        logger.debug("User found: {}", buyer.getUsername());
        // 查询商品
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("商品不存在！"));
        logger.debug("Product found: {}", product.getName());
        // 库存检查
        if (quantity > product.getStock()) {
            throw new IllegalStateException("库存不足！");
        }

        // 创建订单
        Order order = new Order();
        order.setBuyer(buyer);
        order.setSeller(product.getSeller());
        order.setQuantity(quantity);
        order.setProduct(product);
        order.setTotalPrice(product.getPrice().multiply( new BigDecimal(quantity)));
        order.setStatus(Order.Status.completed);
        order.setCreateTime(LocalDateTime.now());
        logger.debug("Order object created: {}", order);


        // 保存订单
        try {
            orderRepository.save(order);
            logger.info("订单创建成功: " + order.getOrderId());
        } catch (Exception e) {
            logger.error("Order creation failed: ", e);
            throw new RuntimeException("订单创建失败", e);
        }
        logger.debug("Order saved successfully");

        // 发送库存更新信息到RabbitMQ
        try {
            OrderMessage orderMessage = new OrderMessage(order.getOrderId(),product.getProductId(), quantity, "stock_update");
            orderProducer.sendOrderMessage(orderMessage);
            logger.info("Order message sent to RabbitMQ: " + orderMessage.getOrderId());
        } catch (Exception e) {
            logger.error("Failed to send order message to RabbitMQ: ", e);
        }

        return order;
    }



    //订单查询服务
    public Order getOrderById(Long orderId) {
        String rediskey = "orderId:" + orderId;

        //从Redis中获取商品
        Order order = (Order) redisTemplate.opsForValue().get(rediskey);

        //检查缓存中是否存在空值或订单信息
        if (order != null) {
            System.out.println("从缓存中获取订单信息：" + orderId);
            return order;
        }

        //缓存中不存在订单信息，则从数据库中查询订单信息
        order = orderRepository.getOrderByOrderId(orderId);

        //将查询到订单信息缓存到redis中
        //设置缓存随机过期时间，10-15分钟
        long randomExpire = 10 + new Random().nextInt(5);
        if (order != null) {
            redisTemplate.opsForValue().set(rediskey, order, randomExpire, TimeUnit.MINUTES);
        } else {
            redisTemplate.opsForValue().set(rediskey, "", 5, TimeUnit.MINUTES);
        }
        return order;

    }

}


