package com.platform.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.entity.User;
import com.platform.user.mapper.UserMapper;
import com.platform.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
    
    @Override
    public User findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }
    
    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        if (findByUsername(user.getUsername()) != null) {
            return false;
        }
        
        // 检查邮箱是否已存在
        if (StringUtils.hasText(user.getEmail()) && findByEmail(user.getEmail()) != null) {
            return false;
        }
        
        // 检查手机号是否已存在
        if (StringUtils.hasText(user.getPhone()) && findByPhone(user.getPhone()) != null) {
            return false;
        }
        
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1); // 设置为正常状态
        
        return save(user);
    }
    
    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = getById(userId);
        if (user == null) {
            return false;
        }
        
        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }
        
        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        return updateById(user);
    }
    
    @Override
    public IPage<User> pageQuery(int pageNum, int pageSize, String keyword) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(keyword)) {
            wrapper.like(User::getUsername, keyword)
                   .or().like(User::getNickname, keyword)
                   .or().like(User::getEmail, keyword);
        }
        
        wrapper.orderByDesc(User::getCreatedAt);
        return page(page, wrapper);
    }
}