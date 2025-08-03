package com.platform.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.user.entity.Creator;

public interface CreatorService extends IService<Creator> {
    
    Creator findByUserId(Long userId);
    
    boolean applyCreator(Creator creator);
    
    boolean approveCreator(Long creatorId);
    
    boolean rejectCreator(Long creatorId, String reason);
}