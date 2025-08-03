package com.platform.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.entity.User;

public interface UserService extends IService<User> {
    
    User findByUsername(String username);
    
    User findByEmail(String email);
    
    User findByPhone(String phone);
    
    boolean register(User user);
    
    boolean updatePassword(Long userId, String oldPassword, String newPassword);
    
    IPage<User> pageQuery(int pageNum, int pageSize, String keyword);
}