package com.zhp.taotaole.repository;

import com.zhp.taotaole.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * ClassName: UserRepository
 * Package: com.zhp.taotaole.repository
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/2 20:44
 * @Version 1.0
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
