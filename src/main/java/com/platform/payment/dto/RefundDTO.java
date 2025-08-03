package com.platform.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "退款请求DTO")
public class RefundDTO {
    
    @Schema(description = "订单号")
    private String orderNo;
    
    @Schema(description = "退款金额")
    private BigDecimal refundAmount;
    
    @Schema(description = "退款原因")
    private String refundReason;
    
    // Getters and Setters
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    
    public BigDecimal getRefundAmount() { return refundAmount; }
    public void setRefundAmount(BigDecimal refundAmount) { this.refundAmount = refundAmount; }
    
    public String getRefundReason() { return refundReason; }
    public void setRefundReason(String refundReason) { this.refundReason = refundReason; }
}