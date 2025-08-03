package com.platform.content.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("categories")
public class Category implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long parentId;
    private String type;
    private String name;
    private String icon;
    private String description;
    private Integer sortOrder;
    private Integer isShow;
    private String extra;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(exist = false)
    private List<Category> children;
}