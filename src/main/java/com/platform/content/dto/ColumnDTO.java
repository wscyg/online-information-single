package com.platform.content.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ColumnDTO {
    @NotBlank(message = "标题不能为空")
    private String title;

    private String subtitle;
    private String cover;
    private String intro;
    private String detail;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    private BigDecimal originPrice;
    private BigDecimal vipPrice;
    private Integer isFreeVip;

    private Long categoryId;
    private String tags;
    private String updateFrequency;
}