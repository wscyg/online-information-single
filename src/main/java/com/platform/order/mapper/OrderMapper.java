package com.platform.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    Order findByOrderNo(@Param("orderNo") String orderNo);
    
    List<Order> findByUserId(@Param("userId") Long userId);
    
    List<Order> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status);
}