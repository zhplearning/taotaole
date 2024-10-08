package com.zhp.taotaole.configuration;

/**
 * ClassName: RedisConfig
 * Package: com.zhp.taotaole.configuration
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/3 20:36
 * @Version 1.0
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 配置 Jackson2JsonRedisSerializer，并指定反序列化的目标类型
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();

        // 注册 Java 8 日期模块和其他模块
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new ParameterNamesModule());

        // 使用安全的多态类型验证器替代 enableDefaultTyping
        BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Object.class) // 允许 Object 类型作为基础类型
                .build();
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL); // 激活安全的默认类型

        // 配置序列化器
        serializer.setObjectMapper(mapper);

        // 设置键、值的序列化器
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }
}
