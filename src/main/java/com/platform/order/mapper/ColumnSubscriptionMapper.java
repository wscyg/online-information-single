package com.platform.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.order.entity.ColumnSubscription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ColumnSubscriptionMapper extends BaseMapper<ColumnSubscription> {
    
    ColumnSubscription findByUserIdAndColumnId(@Param("userId") Long userId, @Param("columnId") Long columnId);
    
    List<ColumnSubscription> findByUserId(@Param("userId") Long userId);
    
    List<ColumnSubscription> findByColumnId(@Param("columnId") Long columnId);
}