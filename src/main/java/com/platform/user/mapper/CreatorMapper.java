package com.platform.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.user.entity.Creator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CreatorMapper extends BaseMapper<Creator> {
    
    Creator findByUserId(@Param("userId") Long userId);
}