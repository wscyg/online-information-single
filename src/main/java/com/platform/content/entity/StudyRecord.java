package com.platform.content.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("study_records")
public class StudyRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private Long contentId;
    private Long columnId;
    private Integer duration;
    private Integer progress;
    private Integer lastPosition;
    private Integer isFinished;
    private LocalDateTime finishedAt;
    private String device;
    private String extra;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}