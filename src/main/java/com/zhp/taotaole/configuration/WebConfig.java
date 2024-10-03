package com.zhp.taotaole.configuration;

/**
 * ClassName: WebConfig
 * Package: com.zhp.taotaole.configuration
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/3 15:16
 * @Version 1.0
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")  // 前端的地址
                        .allowedMethods("GET", "POST")
                        .allowCredentials(true);
            }
        };
    }

}

