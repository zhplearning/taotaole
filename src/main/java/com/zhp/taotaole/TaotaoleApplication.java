package com.zhp.taotaole;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableRabbit    //rabbitmq消息队列监听的注解
public class TaotaoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaotaoleApplication.class, args);
    }

}
