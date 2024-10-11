package com.zhp.taotaole.configuration;

/**
 * ClassName: SecurityConfig
 * Package: com.zhp.taotaole.configuration
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/4 13:34
 * @Version 1.0
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // 允许访问 swagger 文档
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").authenticated()
                // 允许匿名访问部分公开的 API
                .antMatchers("/api/public/**").permitAll()
                // 保护需要认证的 API
                .antMatchers("/api/**").authenticated()
                .antMatchers("/api/orders/create").hasAuthority("CREATE_ORDER")
                .anyRequest().authenticated()// 其他请求需要认证
                .and()
                .formLogin();   //启用表单登录
    }
}

