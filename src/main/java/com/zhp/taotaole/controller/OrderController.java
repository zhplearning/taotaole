package com.zhp.taotaole.controller;

import com.zhp.taotaole.entity.Order;
import com.zhp.taotaole.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    //处理创建订单的post请求
    @PreAuthorize("hasRole('ROLE_USER') or hasAuthority('CREATE_ORDER')")
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestParam Long buyerId,
                                             @RequestParam Long productId,
                                             @RequestParam int quantity){
        try{
            //调用OrderService创建订单
            Order order=orderService.createOrder(buyerId,productId,quantity);
            //返回成功响应，包含创建的订单信息
            return ResponseEntity.ok(order);
        }catch (Exception e){
            //处理订单创建中的错误，返回适当的http错误响应
            // 打印异常以便调试
            e.printStackTrace();  // 或者使用日志系统记录异常 log.error("Order creation failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
