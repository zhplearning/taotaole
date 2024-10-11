package com.zhp.taotaole.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * ClassName: OrderMessage
 * Package: com.zhp.taotaole.entity
 * Description:
 *
 * @Author 詹慧萍
 * @Create 2024/10/9 15:29
 * @Version 1.0
 */
@Getter
@Setter

//创建一个简单的消息类，用于传递订单的相关信息
public class OrderMessage implements Serializable {
    private Long orderId;
    //private Long buyerId;
    private Long productId;
    private int quantity;
    private String messageType;

    public OrderMessage(Long orderId,Long productId,int quantity,String messageType){
        this.orderId=orderId;
        //this.buyerId=buyerId;
        this.productId=productId;
        this.quantity=quantity;
        this.messageType=messageType;
    }

}
