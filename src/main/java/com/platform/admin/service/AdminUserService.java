package com.platform.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.dto.UserQueryDTO;
import com.platform.admin.vo.UserListVO;

/**
 * 管理员用户服务
 */
public interface AdminUserService {
    IPage<UserListVO> getUserList(UserQueryDTO queryDTO);
    void disableUser(Long userId);
    void enableUser(Long userId);
    String resetPassword(Long userId);
}