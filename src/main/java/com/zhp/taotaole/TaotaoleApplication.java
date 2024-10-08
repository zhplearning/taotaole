package com.zhp.taotaole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TaotaoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaotaoleApplication.class, args);
    }

}
