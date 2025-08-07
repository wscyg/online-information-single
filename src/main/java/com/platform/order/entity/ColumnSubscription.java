package com.platform.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("column_subscriptions")
public class ColumnSubscription extends BaseEntity {
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("column_id")
    private Long columnId;
    
    @TableField("order_id")
    private Long orderId;
    
    @TableField("order_no")
    private String orderNo;
    
    @TableField("expire_time")
    private LocalDateTime expireTime;
    
    @TableField("status")
    private String status; // PENDING, ACTIVE, EXPIRED, CANCELLED
    
    @TableField("pay_status")
    private String payStatus; // PENDING, SUCCESS, FAILED
}