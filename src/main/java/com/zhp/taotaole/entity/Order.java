package com.zhp.taotaole.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * ClassName: Order
 * Package: com.zhp.taotaole.entity
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/8 21:41
 * @Version 1.0
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    //订单与商品的关系，多对一
    @ManyToOne
    @JsonBackReference("productReference")
    @JoinColumn(name="product_id")
    private Product product;

    //订单与买家的关系，多对一
    @ManyToOne
    @JsonBackReference("buyerReference")
    @JoinColumn(name="buyer_id")
    private User buyer;

    //订单与卖家的关系，多对一
    @ManyToOne
    @JsonBackReference("sellerReference")
    @JoinColumn(name="seller_id")
    private User seller;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "create_time",updatable = false)
    private LocalDateTime createTime;

    @PrePersist
    protected void onCreate(){
        createTime=LocalDateTime.now();
    }

    public enum Status{
        pending,completed,cancelled;
    }

}
