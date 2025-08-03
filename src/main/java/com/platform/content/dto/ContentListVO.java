package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "内容列表视图对象")
public class ContentListVO {

    @Schema(description = "内容ID")
    private Long id;

    @Schema(description = "专栏ID")
    private Long columnId;

    @Schema(description = "专栏名称")
    private String columnName;

    @Schema(description = "内容类型 text-图文 video-视频 audio-音频 live-直播")
    private String type;

    @Schema(description = "内容标题")
    private String title;

    @Schema(description = "内容封面")
    private String cover;

    @Schema(description = "内容摘要")
    private String summary;

    @Schema(description = "视频时长(秒)")
    private Integer videoDuration;

    @Schema(description = "音频时长(秒)")
    private Integer audioDuration;

    @Schema(description = "是否免费 0-付费 1-免费")
    private Integer isFree;

    @Schema(description = "是否可预览 0-不可预览 1-可预览")
    private Integer isPreview;

    @Schema(description = "排序顺序")
    private Integer sortOrder;

    @Schema(description = "浏览量")
    private Integer viewCount;

    @Schema(description = "点赞数")
    private Integer likeCount;

    @Schema(description = "评论数")
    private Integer commentCount;

    @Schema(description = "分享数")
    private Integer shareCount;

    @Schema(description = "收藏数")
    private Integer collectCount;

    @Schema(description = "学习数")
    private Integer studyCount;

    @Schema(description = "完成数")
    private Integer finishCount;

    @Schema(description = "标签，多个用逗号分隔")
    private String tags;

    @Schema(description = "状态 0-草稿 1-已发布 2-已下线")
    private Integer status;

    @Schema(description = "发布时间")
    private LocalDateTime publishedAt;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "创建者ID")
    private Long creatorId;

    @Schema(description = "创建者昵称")
    private String creatorNickname;

    @Schema(description = "创建者头像")
    private String creatorAvatar;

    @Schema(description = "用户是否已点赞")
    private Boolean isLiked;

    @Schema(description = "用户是否已收藏")
    private Boolean isCollected;

    @Schema(description = "用户学习进度")
    private Integer studyProgress;
}