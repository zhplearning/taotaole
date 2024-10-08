package com.zhp.taotaole.controller;

import com.zhp.taotaole.entity.User;
import com.zhp.taotaole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ClassName: UserController
 * Package: com.zhp.taotaole.controller
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/2 20:51
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        if(user.getRole()==null){
            user.setRole(User.Role.buyer);
        }
        if(user.getStatus()==null){
            user.setStatus(User.Status.active);
        }
        return userService.saveUser(user);
    }

//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//        User foundUser = userService.findByEmail(user.getEmail());
//        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
//            return "Login successful";
//        }
//        return "Invalid credentials";
//    }


    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/findByEmail/{email}")
    public User findByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }


}
