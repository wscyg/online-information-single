package com.platform.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("orders")
public class Order extends BaseEntity {

    private String orderNo;
    private String tradeNo;
    private Long userId;
    private String orderType;
    private Long productId;
    private Long columnId;  // 专栏ID，用于订阅关联
    private String productSnapshot;
    private Integer quantity;
    private String currency;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal couponAmount;
    private BigDecimal payAmount;
    private String payMethod;
    private String payChannel;
    private Integer payStatus;
    private LocalDateTime payAt;
    private LocalDateTime expireAt;
    private String deviceInfo;
    private String clientIp;
    private Long couponId;
    private Long inviterId;
    private BigDecimal commissionAmount;
    private Integer refundStatus;
    private BigDecimal refundAmount;
    private String refundReason;
    private LocalDateTime refundAt;
    private String comment;
    private String extra;
}