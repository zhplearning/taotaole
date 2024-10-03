package com.zhp.taotaole.service;

import com.zhp.taotaole.entity.Product;
import com.zhp.taotaole.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ClassName: ProductService
 * Package: com.zhp.taotaole.service
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/3 10:17
 * @Version 1.0
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> getByName(String name){
        return productRepository.getByName(name);
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }
}
