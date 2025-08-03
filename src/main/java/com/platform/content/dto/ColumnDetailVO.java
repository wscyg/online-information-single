package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "专栏详情")
public class ColumnDetailVO {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "创作者ID")
    private Long creatorId;

    @Schema(description = "创作者名称")
    private String creatorName;

    @Schema(description = "创作者头像")
    private String creatorAvatar;

    @Schema(description = "创作者等级")
    private Integer creatorLevel;

    @Schema(description = "创作者粉丝数")
    private Integer creatorFollowers;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "副标题")
    private String subtitle;

    @Schema(description = "封面图")
    private String cover;

    @Schema(description = "Banner图")
    private String banner;

    @Schema(description = "简介")
    private String intro;

    @Schema(description = "详细介绍")
    private String detail;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "原价")
    private BigDecimal originPrice;

    @Schema(description = "VIP价格")
    private BigDecimal vipPrice;

    @Schema(description = "VIP是否免费")
    private Integer isFreeVip;

    @Schema(description = "订阅数")
    private Integer subscribeCount;

    @Schema(description = "浏览数")
    private Integer viewCount;

    @Schema(description = "内容数量")
    private Integer contentCount;

    @Schema(description = "已完成数量")
    private Integer finishedCount;

    @Schema(description = "更新频率说明")
    private String updateFrequency;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "是否推荐")
    private Integer isRecommend;

    @Schema(description = "是否热门")
    private Integer isHot;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "发布时间")
    private LocalDateTime publishedAt;

    @Schema(description = "是否已订阅")
    private Boolean isSubscribed;
}