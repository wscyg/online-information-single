package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "订阅状态VO")
public class SubscriptionStatusVO {
    
    @Schema(description = "是否已订阅")
    private Boolean subscribed;
    
    @Schema(description = "订阅状态：0-未订阅，1-已订阅，2-订阅过期")
    private Integer status;
    
    // Getters and Setters
    public Boolean getSubscribed() { return subscribed; }
    public void setSubscribed(Boolean subscribed) { this.subscribed = subscribed; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}