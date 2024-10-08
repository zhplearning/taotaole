package com.zhp.taotaole.controller;

import com.zhp.taotaole.entity.Order;
import com.zhp.taotaole.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

/**
 * ClassName: OrderController
 * Package: com.zhp.taotaole.controller
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/8 22:46
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getById/{productId}")
    public Order getOrderById(@PathVariable Long productId){
        return orderService.getOrderById(productId);
    }

}
