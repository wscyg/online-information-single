package com.platform.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.auth.entity.UserOAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserOAuthMapper extends BaseMapper<UserOAuth> {
}