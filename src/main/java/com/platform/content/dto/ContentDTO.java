package com.platform.content.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ContentDTO {
    @NotNull(message = "专栏ID不能为空")
    private Long columnId;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String type = "article";
    private String cover;
    private String summary;
    private String content;
    private String markdownContent;

    private String videoUrl;
    private Integer videoDuration;

    private Integer isFree = 0;
    private Integer isPreview = 0;
    private String previewContent;

    private Integer sortOrder = 0;
    private String tags;
}