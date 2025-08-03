package com.platform.content.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user_actions")
public class UserAction implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private String action;
    private String type;
    private Long targetId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}