package com.platform.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class PaymentRequest {
    
    @Schema(description = "商户订单号", example = "ORDER20250806001", required = true)
    @NotBlank(message = "订单号不能为空")
    private String orderNo;
    
    @Schema(description = "订单标题", example = "专栏订阅", required = true)
    @NotBlank(message = "商品标题不能为空")
    private String subject;
    
    @Schema(description = "订单描述", example = "专栏订阅支付")
    private String body;
    
    @Schema(description = "支付金额(元)", example = "99.00", required = true)
    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0.01", message = "金额必须大于0.01")
    private BigDecimal amount;
    
    @Schema(description = "回传参数", example = "subscription_callback_param")
    private String passbackParams;
    
    public PaymentRequest() {}
    
    public PaymentRequest(String orderNo, String subject, String body, BigDecimal amount, String passbackParams) {
        this.orderNo = orderNo;
        this.subject = subject;
        this.body = body;
        this.amount = amount;
        this.passbackParams = passbackParams;
    }
    
    // Getters and Setters
    public String getOrderNo() {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getPassbackParams() {
        return passbackParams;
    }
    
    public void setPassbackParams(String passbackParams) {
        this.passbackParams = passbackParams;
    }
}