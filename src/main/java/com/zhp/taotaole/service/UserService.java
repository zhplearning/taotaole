package com.zhp.taotaole.service;

import com.zhp.taotaole.entity.User;
import com.zhp.taotaole.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ClassName: UserService
 * Package: com.zhp.taotaole.service
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/2 20:48
 * @Version 1.0
 */
@Service
public class UserService {

    //private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserRepository userRepository;
   /* public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder(); // 实例化密码加密器
    }

    public void registerUser(User user) {
        // 加密用户密码
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // 将加密后的密码设置回用户对象

        // 保存用户到数据库
        userRepository.save(user);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        // 验证密码是否匹配
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }*/


    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
    @Cacheable(value = "users",key = "#email")
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean validateUser(String email, String password) {
        // 假设你使用了 UserRepository 从数据库中查询用户信息
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }


}
