package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "用户行为请求")
public class ActionRequest {

    @Schema(hidden = true)
    private Long userId;

    @Schema(hidden = true)
    private String action;

    @Schema(description = "类型", required = true)
    @NotBlank(message = "类型不能为空")
    private String type;

    @Schema(description = "目标ID", required = true)
    @NotNull(message = "目标ID不能为空")
    private Long targetId;
}