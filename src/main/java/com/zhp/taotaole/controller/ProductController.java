package com.zhp.taotaole.controller;

import com.zhp.taotaole.entity.Product;
import com.zhp.taotaole.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ClassName: ProductController
 * Package: com.zhp.taotaole.controller
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/3 10:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/addPro")
    public Product createProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @GetMapping("/findAll")
    public List<Product> selectAll(){
        return productService.findAll();
    }

    @GetMapping("/{name}")
    public Product find(@PathVariable String name){
        return productService.findByName(name);
    }

    @GetMapping("/getProById/{productId}")
    public Product getProductById(@PathVariable Long productId){
        return productService.getProductById(productId);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody Product newData) {
        // 调用服务层，更新用户信息
        productService.updateProduct(productId, newData);

        // 返回响应
        return ResponseEntity.ok("Product updated and cache invalidated.");
    }
}
