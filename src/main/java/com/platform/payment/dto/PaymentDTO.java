package com.platform.payment.dto;

import java.math.BigDecimal;
import java.util.Map;

public class PaymentDTO {
    private String orderNo;
    private BigDecimal amount;
    private String subject;
    private String body;
    private String payType;
    private String payMethod;
    private String payChannel;
    private String clientIp;
    private String returnUrl;
    private String quitUrl;
    private Map<String, Object> extra;
    
    // Getters and Setters
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    
    public String getPayType() { return payType; }
    public void setPayType(String payType) { this.payType = payType; }
    
    public String getPayMethod() { return payMethod; }
    public void setPayMethod(String payMethod) { this.payMethod = payMethod; }
    
    public String getPayChannel() { return payChannel; }
    public void setPayChannel(String payChannel) { this.payChannel = payChannel; }
    
    public String getClientIp() { return clientIp; }
    public void setClientIp(String clientIp) { this.clientIp = clientIp; }
    
    public String getReturnUrl() { return returnUrl; }
    public void setReturnUrl(String returnUrl) { this.returnUrl = returnUrl; }
    
    public String getQuitUrl() { return quitUrl; }
    public void setQuitUrl(String quitUrl) { this.quitUrl = quitUrl; }
    
    public Map<String, Object> getExtra() { return extra; }
    public void setExtra(Map<String, Object> extra) { this.extra = extra; }
}