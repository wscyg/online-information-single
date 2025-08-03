package com.platform.payment.vo;

import java.math.BigDecimal;

public class PaymentVO {
    private String orderNo;
    private BigDecimal amount;
    private String payType; // form/qrcode/app
    private String payUrl;
    private String codeUrl;
    private String appParams;
    private Long expireTime;
    
    // Getters and Setters
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getPayType() { return payType; }
    public void setPayType(String payType) { this.payType = payType; }
    
    public String getPayUrl() { return payUrl; }
    public void setPayUrl(String payUrl) { this.payUrl = payUrl; }
    
    public String getCodeUrl() { return codeUrl; }
    public void setCodeUrl(String codeUrl) { this.codeUrl = codeUrl; }
    
    public String getAppParams() { return appParams; }
    public void setAppParams(String appParams) { this.appParams = appParams; }
    
    public Long getExpireTime() { return expireTime; }
    public void setExpireTime(Long expireTime) { this.expireTime = expireTime; }
}