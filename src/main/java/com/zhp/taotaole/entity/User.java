package com.zhp.taotaole.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: User
 * Package: com.zhp.taotaole.entity
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/2 20:19
 * @Version 1.0
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)      //数据库自增
    private Long userId;

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    //卖家与商品的关系，一对多
    @OneToMany(mappedBy = "seller")
    @JsonManagedReference
    private List<Product> products;

    //卖家与订单的关系，一对多
    @OneToMany(mappedBy = "seller")
    @JsonManagedReference("sellerReference")
    private List<Order> sellerOrders;

    //买家与订单的关系，一对多
    @OneToMany(mappedBy = "buyer")
    @JsonManagedReference("buyerReference")
    private List<Order> buyerOrders;


    @Column(name = "create_time",updatable = false)
    private LocalDateTime createTime;

    @PrePersist
    protected void oncreate(){
        this.createTime=LocalDateTime.now();
    }

    public enum Role{
        buyer,seller;
    }

    public enum Status{
        active,inactive;
    }



}
