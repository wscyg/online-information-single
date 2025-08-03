package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "内容详情")
public class ContentDetailVO {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "专栏ID")
    private Long columnId;

    @Schema(description = "专栏标题")
    private String columnTitle;

    @Schema(description = "内容类型")
    private String type;

    @Schema(description = "标题")
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

    @Schema(description = "视频时长")
    private Integer videoDuration;

    @Schema(description = "音频地址")
    private String audioUrl;

    @Schema(description = "音频时长")
    private Integer audioDuration;

    @Schema(description = "附件地址")
    private String attachmentUrls;

    @Schema(description = "是否免费")
    private Integer isFree;

    @Schema(description = "浏览数")
    private Integer viewCount;

    @Schema(description = "点赞数")
    private Integer likeCount;

    @Schema(description = "评论数")
    private Integer commentCount;

    @Schema(description = "分享数")
    private Integer shareCount;

    @Schema(description = "收藏数")
    private Integer collectCount;

    @Schema(description = "学习人数")
    private Integer studyCount;

    @Schema(description = "完成人数")
    private Integer finishCount;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "发布时间")
    private LocalDateTime publishedAt;

    @Schema(description = "是否已学习")
    private Boolean hasStudied;

    @Schema(description = "学习进度")
    private Integer studyProgress;
}