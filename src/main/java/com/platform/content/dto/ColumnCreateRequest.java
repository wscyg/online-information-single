package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "创建专栏请求")
public class ColumnCreateRequest {

    @Schema(hidden = true)
    private Long creatorId;

    @Schema(description = "类型", required = true)
    @NotBlank(message = "类型不能为空")
    private String type;

    @Schema(description = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "副标题")
    private String subtitle;

    @Schema(description = "封面图")
    private String cover;

    @Schema(description = "Banner图")
    private String banner;

    @Schema(description = "简介", required = true)
    @NotBlank(message = "简介不能为空")
    private String intro;

    @Schema(description = "详细介绍")
    private String detail;

    @Schema(description = "价格", required = true)
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @Schema(description = "原价")
    private BigDecimal originPrice;

    @Schema(description = "VIP价格")
    private BigDecimal vipPrice;

    @Schema(description = "VIP是否免费")
    private Integer isFreeVip;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "更新频率说明")
    private String updateFrequency;
}