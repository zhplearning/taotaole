package com.zhp.taotaole.configuration;

/**
 * ClassName: BloomFilterConfig
 * Package: com.zhp.taotaole.configuration
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/3 21:45
 * @Version 1.0
 */
//import com.google.common.hash.BloomFilter;
//import com.google.common.hash.Funnels;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class BloomFilterConfig {
//
//    @Bean
//    public BloomFilter<Long> bloomFilter() {
//        // 初始化布隆过滤器，最多支持100万商品ID，误判率0.01
//        return BloomFilter.create(Funnels.longFunnel(), 1000000, 0.01);
//    }
//}
