package com.zhp.taotaole.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: Product
 * Package: com.zhp.taotaole.entity
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/3 10:00
 * @Version 1.0
 */
@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String name;
    private String description;
    private BigDecimal price;
    private Long stock;

    //商品与卖家的关系，多对一
    @ManyToOne
    @JoinColumn(name = "seller_id",nullable = false)
    @JsonBackReference
    private User seller;

    //商品与订单的关系，一对多
    @OneToMany(mappedBy = "product")
    @JsonManagedReference("productReference")
    private List<Order> orders;


    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "create_time",updatable = false)
    private LocalDateTime createTime;

    @PrePersist
    protected void onCreate(){
        this.createTime=LocalDateTime.now();
    }


    public enum Status{
        available,removed,sold;
    }

}
