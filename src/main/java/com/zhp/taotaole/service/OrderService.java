package com.zhp.taotaole.service;

import com.zhp.taotaole.entity.Order;
import com.zhp.taotaole.entity.Product;
import com.zhp.taotaole.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    //订单查询服务
    public Order getOrderById(Long orderId){
        String rediskey="orderId:"+orderId;

        //从Redis中获取商品
        Order order=(Order)redisTemplate.opsForValue().get(rediskey);

        //检查缓存中是否存在空值或订单信息
        if(order!=null){
            System.out.println("从缓存中获取订单信息："+orderId);
            return order;
        }

        //缓存中不存在订单信息，则从数据库中查询订单信息
        order=orderRepository.getOrderByOrderId(orderId);

        //将查询到订单信息缓存到redis中
        //设置缓存随机过期时间，10-15分钟
        long randomExpire=10+new Random().nextInt(5);
        if(order!=null){
            redisTemplate.opsForValue().set(rediskey,order,randomExpire, TimeUnit.MINUTES);
        }else{
            redisTemplate.opsForValue().set(rediskey,"",5,TimeUnit.MINUTES);
        }
        return order;

    }
}
