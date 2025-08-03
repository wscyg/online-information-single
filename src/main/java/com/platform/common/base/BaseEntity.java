package com.platform.common.base;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 临时注释时间字段，避免数据库字段映射问题
    // @TableField(value = "create_time", fill = FieldFill.INSERT)
    // private LocalDateTime createdAt;

    // @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    // private LocalDateTime updatedAt;

    // 临时注释掉逻辑删除字段，避免数据库表结构问题
    // @TableLogic
    // @TableField(fill = FieldFill.INSERT)
    // private Integer deleted;

    // 临时注释version字段，等数据库表添加了字段再启用
    // @Version
    // private Integer version;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    // 临时注释时间字段的getter/setter
    /*
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    */
    
    // 临时注释掉deleted字段的getter/setter
    /*
    public Integer getDeleted() {
        return deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
    */
    
    // 临时注释version字段的getter/setter
    /*
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    */
}