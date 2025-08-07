package com.platform.order.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VipOrderVO {
    private String orderId;
    private String vipType;
    private BigDecimal price;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime expireTime;
}