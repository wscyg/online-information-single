package com.platform.admin.dto;

import lombok.Data;
import com.platform.common.base.BaseQuery;

/**
 * 用户查询DTO
 */
@Data
public class UserQueryDTO extends BaseQuery {
    private String username;
    private String email;
    private String phone;
    private Integer status;
}