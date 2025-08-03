package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "创建内容请求")
public class ContentCreateRequest {

    @Schema(description = "专栏ID", required = true)
    @NotNull(message = "专栏ID不能为空")
    private Long columnId;

    @Schema(description = "内容类型", required = true)
    @NotBlank(message = "内容类型不能为空")
    private String type;

    @Schema(description = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "Markdown内容")
    private String markdownContent;

    @Schema(description = "视频地址")
    private String videoUrl;

    @Schema(description = "视频时长(秒)")
    private Integer videoDuration;

    @Schema(description = "音频地址")
    private String audioUrl;

    @Schema(description = "音频时长(秒)")
    private Integer audioDuration;

    @Schema(description = "附件地址")
    private String attachmentUrls;

    @Schema(description = "是否免费")
    private Integer isFree;

    @Schema(description = "是否可预览")
    private Integer isPreview;

    @Schema(description = "预览内容")
    private String previewContent;

    @Schema(description = "排序")
    private Integer sortOrder;

    @Schema(description = "标签")
    private String tags;
}