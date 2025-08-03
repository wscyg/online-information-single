package com.platform.content.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("columns")
public class Column implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long creatorId;
    private String type;
    private String title;
    private String subtitle;
    private String cover;
    private String banner;
    private String intro;
    private String detail;
    private BigDecimal price;
    private BigDecimal originPrice;
    private BigDecimal vipPrice;
    private Integer isFreeVip;
    private BigDecimal commissionRate;
    private Integer subscribeCount;
    private Integer viewCount;
    private Integer fakeSubscribeCount;
    private Integer contentCount;
    private Integer finishedCount;
    private String updateFrequency;
    private LocalDate estimateFinishAt;
    private Long categoryId;
    private String tags;
    private String seoTitle;
    private String seoKeywords;
    private String seoDescription;
    private Integer sortOrder;
    private Integer isRecommend;
    private Integer isHot;
    private Integer status;
    private String auditReason;
    private LocalDateTime publishedAt;
    private String extra;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private String creatorName;

    @TableField(exist = false)
    private String creatorAvatar;
}