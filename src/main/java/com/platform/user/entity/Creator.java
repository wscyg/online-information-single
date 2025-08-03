package com.platform.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.common.base.BaseEntity;

@TableName("creators")
public class Creator extends BaseEntity {
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("name")
    private String name;
    
    @TableField("description")
    private String description;
    
    @TableField("avatar")
    private String avatar;
    
    @TableField("status")
    private Integer status;
    
    @TableField("apply_reason")
    private String applyReason;
    
    // Getter and Setter methods
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getApplyReason() {
        return applyReason;
    }
    
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }
}