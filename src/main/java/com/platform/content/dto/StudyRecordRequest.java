package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "学习记录请求")
public class StudyRecordRequest {

    @Schema(hidden = true)
    private Long userId;

    @Schema(hidden = true)
    private Long contentId;

    @Schema(description = "学习时长(秒)", required = true)
    @NotNull(message = "学习时长不能为空")
    private Integer duration;

    @Schema(description = "进度(%)")
    private Integer progress;

    @Schema(description = "最后位置")
    private Integer lastPosition;

    @Schema(description = "设备")
    private String device;
}