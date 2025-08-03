package com.platform.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("contents")
public class Content extends BaseEntity {

    private Long columnId;
    private String type;
    private String title;
    private String cover;
    private String summary;
    private String content;
    private String markdownContent;
    private String videoUrl;
    private Integer videoDuration;
    private String audioUrl;
    private Integer audioDuration;
    private String attachmentUrls;
    private Integer isFree;
    private Integer isPreview;
    private String previewContent;
    private Integer sortOrder;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer shareCount;
    private Integer collectCount;
    private Integer studyCount;
    private Integer finishCount;
    private String tags;
    private Integer status;
    private LocalDateTime publishedAt;
    private String extra;
}