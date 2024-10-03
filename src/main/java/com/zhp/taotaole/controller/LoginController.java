package com.zhp.taotaole.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";  // 这里 "login" 是视图的逻辑名称
    }
}

