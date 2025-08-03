package com.platform.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.user.entity.Creator;
import com.platform.user.mapper.CreatorMapper;
import com.platform.user.service.CreatorService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class CreatorServiceImpl extends ServiceImpl<CreatorMapper, Creator> implements CreatorService {
    
    @Resource
    private CreatorMapper creatorMapper;
    
    @Override
    public Creator findByUserId(Long userId) {
        return creatorMapper.findByUserId(userId);
    }
    
    @Override
    public boolean applyCreator(Creator creator) {
        // 检查用户是否已经申请过
        if (findByUserId(creator.getUserId()) != null) {
            return false;
        }
        
        creator.setStatus(0); // 设置为待审核状态
        return save(creator);
    }
    
    @Override
    public boolean approveCreator(Long creatorId) {
        Creator creator = getById(creatorId);
        if (creator == null) {
            return false;
        }
        
        creator.setStatus(1); // 设置为已通过
        return updateById(creator);
    }
    
    @Override
    public boolean rejectCreator(Long creatorId, String reason) {
        Creator creator = getById(creatorId);
        if (creator == null) {
            return false;
        }
        
        creator.setStatus(2); // 设置为已拒绝
        creator.setApplyReason(reason);
        return updateById(creator);
    }
}