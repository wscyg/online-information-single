package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.admin.dto.UserQueryDTO;
import com.platform.admin.service.AdminUserService;
import com.platform.admin.vo.UserListVO;
import org.springframework.stereotype.Service;

/**
 * 管理员用户服务实现
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Override
    public IPage<UserListVO> getUserList(UserQueryDTO queryDTO) {
        Page<UserListVO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        // TODO: 实现用户查询逻辑
        return page;
    }

    @Override
    public void disableUser(Long userId) {
        // TODO: 实现禁用用户逻辑
    }

    @Override
    public void enableUser(Long userId) {
        // TODO: 实现启用用户逻辑
    }

    @Override
    public String resetPassword(Long userId) {
        // TODO: 实现重置密码逻辑
        return "123456";
    }
}