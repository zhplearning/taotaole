package com.zhp.taotaole.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "seller_id",nullable = false)
    @JsonBackReference
    private User seller;


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
