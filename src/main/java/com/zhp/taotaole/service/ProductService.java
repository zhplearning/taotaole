package com.zhp.taotaole.service;

import com.google.common.hash.BloomFilter;
import com.zhp.taotaole.entity.Product;
import com.zhp.taotaole.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    // 注入 RedisTemplate
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /*@Autowired
    private BloomFilter<Long> bloomFilter;*/
/*
    @PostConstruct
    public void initBloomFilter() {
        // 从数据库中加载所有商品ID到布隆过滤器中
        List<Long> allProductIds = productRepository.findAllProductIds();
        for (Long productId : allProductIds) {
            bloomFilter.put(productId);
        }
        System.out.println("布隆过滤器初始化完成，共加载商品ID数量：" + allProductIds.size());
    }*/

    public Optional<Product> getByName(String name){
        return productRepository.getByName(name);
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findByName(String name){
        return productRepository.getByName(name);
    }


    // 商品查询服务
    public Product getProductById(Long productId) {
        String redisKey = "product:" + productId;

        // 从Redis中获取商品
        Product product = (Product) redisTemplate.opsForValue().get(redisKey);

        // 检查缓存中是否存在空值或商品
        if(product!=null){
            System.out.println("从redis缓存中获取商品:"+productId);
            return product;
        }

        // 从数据库中查询
        product = productRepository.findById(productId).orElse(null);

        // 将查询到的商品缓存到Redis
        long randomExpire = 10 + new Random().nextInt(5);  // 随机过期时间，10到15分钟
        if (product != null) {
            redisTemplate.opsForValue().set(redisKey, product, randomExpire, TimeUnit.MINUTES);
        } else {
            // 如果数据库中没有该商品，避免缓存穿透，将空值缓存
            redisTemplate.opsForValue().set(redisKey, "", 5, TimeUnit.MINUTES);
        }

        return product;
    }

//    // 商品查询服务
//    public Product getProductById(Long productId) {
//        String redisKey = "product:" + productId;
//
//        /*// 先使用布隆过滤器判断商品是否可能存在
//        if (!bloomFilter.mightContain(productId)) {
//            // 如果布隆过滤器认为商品不存在，直接返回null，避免查询数据库
//            System.out.println("布隆过滤器判断商品不存在，直接返回:" + productId);
//            return null;
//        }*/
//
//        // 从Redis中获取商品
//        Product product = (Product) redisTemplate.opsForValue().get(redisKey);
//
//        // 检查缓存中是否存在商品
//        if (product != null) {
//            System.out.println("从redis缓存中获取商品:" + productId);
//            return product;
//        }
//
//        // 从数据库中查询
//        product = productRepository.findById(productId).orElse(null);
//
//        // 将查询到的商品缓存到Redis，并更新布隆过滤器
//        long randomExpire = 10 + new Random().nextInt(5);  // 随机过期时间，10到15分钟
//        if (product != null) {
//            redisTemplate.opsForValue().set(redisKey, product, randomExpire, TimeUnit.MINUTES);
//
//           /* // 如果商品存在，加入布隆过滤器，以避免后续的穿透
//            bloomFilter.put(productId);*/
//        } else {
//            // 如果数据库中没有该商品，避免缓存穿透，将空值缓存
//            redisTemplate.opsForValue().set(redisKey, "", 5, TimeUnit.MINUTES);
//        }
//
//        return product;
//    }


}
