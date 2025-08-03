package com.platform.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    User findByUsername(@Param("username") String username);
    
    User findByEmail(@Param("email") String email);
    
    User findByPhone(@Param("phone") String phone);
}