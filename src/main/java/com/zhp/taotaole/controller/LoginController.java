package com.zhp.taotaole.controller;

import com.zhp.taotaole.entity.User;
import com.zhp.taotaole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: LoginController
 * Package: com.zhp.taotaole.controller
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/3 11:31
 * @Version 1.0
 */
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  // 使用 RestController 而不是 Controller，返回 JSON 数据而不是视图
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<String> login(@RequestBody User user) {
        // 这里可以添加你的业务逻辑，比如验证用户名和密码
        String email = user.getEmail();
        String password = user.getPassword();
        if (userService.validateUser(email, password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

    }
}

