package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "创建评论请求")
public class CommentCreateRequest {

    @Schema(description = "评论类型", required = true)
    @NotBlank(message = "评论类型不能为空")
    private String type;

    @Schema(description = "目标ID", required = true)
    @NotNull(message = "目标ID不能为空")
    private Long targetId;

    @Schema(description = "父评论ID")
    private Long parentId;

    @Schema(description = "根评论ID")
    private Long rootId;

    @Schema(description = "评论内容", required = true)
    @NotBlank(message = "评论内容不能为空")
    private String content;

    @Schema(description = "图片列表")
    private String images;

    @Schema(hidden = true)
    private Long userId;

    @Schema(hidden = true)
    private String ip;
}