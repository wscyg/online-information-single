package com.platform.payment.vo;

import java.math.BigDecimal;

public class PaymentResponse {
    
    private String orderNo;
    private BigDecimal amount;
    private String payType;
    private String payUrl;
    private String qrCodeUrl;
    private String status;
    private String message;
    
    public PaymentResponse() {}
    
    public PaymentResponse(String orderNo, BigDecimal amount, String payType, String payUrl, String qrCodeUrl, String status, String message) {
        this.orderNo = orderNo;
        this.amount = amount;
        this.payType = payType;
        this.payUrl = payUrl;
        this.qrCodeUrl = qrCodeUrl;
        this.status = status;
        this.message = message;
    }
    
    // Getters and Setters
    public String getOrderNo() {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getPayType() {
        return payType;
    }
    
    public void setPayType(String payType) {
        this.payType = payType;
    }
    
    public String getPayUrl() {
        return payUrl;
    }
    
    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }
    
    public String getQrCodeUrl() {
        return qrCodeUrl;
    }
    
    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}