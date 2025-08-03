package com.platform.content.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("comments")
public class Comment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String type;
    private Long targetId;
    private Long parentId;
    private Long rootId;
    private Long userId;
    private String content;
    private String images;
    private Integer likeCount;
    private Integer replyCount;
    private Integer isAuthor;
    private Integer isTop;
    private Integer isHot;
    private Integer status;
    private String ip;
    private String location;
    private String device;
    private String extra;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(exist = false)
    private String userNickname;

    @TableField(exist = false)
    private String userAvatar;

    @TableField(exist = false)
    private Boolean isLiked;

    @TableField(exist = false)
    private List<Comment> replies;
}