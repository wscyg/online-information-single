package com.platform.payment.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentCallbackService {
    
    public String handleAlipayCallback(String callbackData) {
        // 处理支付宝回调
        return "success";
    }
    
    public String handleWechatCallback(String callbackData) {
        // 处理微信支付回调
        return "success";
    }
}