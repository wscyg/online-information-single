package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "内容更新请求")
public class ContentUpdateRequest {

    @Schema(description = "内容标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "内容封面")
    private String cover;

    @Schema(description = "内容摘要")
    private String summary;

    @Schema(description = "内容正文")
    private String content;

    @Schema(description = "Markdown内容")
    private String markdownContent;

    @Schema(description = "视频URL")
    private String videoUrl;

    @Schema(description = "视频时长(秒)")
    private Integer videoDuration;

    @Schema(description = "音频URL")
    private String audioUrl;

    @Schema(description = "音频时长(秒)")
    private Integer audioDuration;

    @Schema(description = "附件URLs，多个用逗号分隔")
    private String attachmentUrls;

    @Schema(description = "是否免费 0-付费 1-免费")
    private Integer isFree;

    @Schema(description = "是否可预览 0-不可预览 1-可预览")
    private Integer isPreview;

    @Schema(description = "预览内容")
    private String previewContent;

    @Schema(description = "排序顺序")
    private Integer sortOrder;

    @Schema(description = "标签，多个用逗号分隔")
    private String tags;

    @Schema(description = "状态 0-草稿 1-已发布 2-已下线")
    private Integer status;

    @Schema(description = "扩展信息，JSON格式")
    private String extra;
}