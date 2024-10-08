package com.zhp.taotaole.repository;

import com.zhp.taotaole.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * ClassName: ProductRepository
 * Package: com.zhp.taotaole.repository
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/3 10:16
 * @Version 1.0
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product getByName(String name);
    //List<Long> findAllProductIds();
}
