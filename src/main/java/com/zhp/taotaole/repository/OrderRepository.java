package com.zhp.taotaole.repository;

import com.zhp.taotaole.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ClassName: OrderRepository
 * Package: com.zhp.taotaole.repository
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/8 22:24
 * @Version 1.0
 */
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order getOrderByOrderId(Long productId);
    //void createOrder(OrderRequest orderRequest);
}
