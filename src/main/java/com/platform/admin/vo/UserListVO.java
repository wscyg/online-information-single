package com.platform.admin.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户列表VO
 */
@Data
public class UserListVO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String nickname;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
}