package com.platform.content.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("platform_columns")
public class PlatformColumn extends BaseEntity {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField("creator_id")
    private Long creatorId;
    
    @TableField("title")
    private String title;
    
    @TableField("description")
    private String description;
    
    @TableField("cover_image")
    private String coverImage;
    
    @TableField("price")
    private BigDecimal price;
    
    @TableField("original_price")
    private BigDecimal originalPrice;
    
    @TableField("category_id")
    private Long categoryId;
    
    @TableField("status")
    private Integer status;
    
    @TableField("is_free")
    private Integer isFree;
    
    @TableField("view_count")
    private Long viewCount;
    
    @TableField("subscribe_count")
    private Long subscribeCount;
}