package com.platform.order.dto;

import lombok.Data;

@Data
public class VipOrderDTO {
    private String vipType; // monthly, yearly, lifetime
    private String payType; // alipay, wechat
}