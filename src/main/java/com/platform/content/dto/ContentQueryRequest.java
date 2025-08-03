package com.platform.content.dto;

import com.platform.common.base.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "内容查询请求")
public class ContentQueryRequest extends BaseQuery {

    @Schema(description = "专栏ID")
    private Long columnId;

    @Schema(description = "内容类型 text-图文 video-视频 audio-音频 live-直播")
    private String type;

    @Schema(description = "标题关键词")
    private String title;

    @Schema(description = "标签")
    private String tag;

    @Schema(description = "是否免费 0-付费 1-免费")
    private Integer isFree;

    @Schema(description = "状态 0-草稿 1-已发布 2-已下线")
    private Integer status;

    @Schema(description = "排序字段 create_time-创建时间 view_count-浏览量 like_count-点赞数")
    private String sortBy = "create_time";

    @Schema(description = "排序方向 asc-升序 desc-降序")
    private String sortOrder = "desc";
}