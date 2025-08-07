package com.platform.order.service;

import com.platform.order.dto.VipOrderDTO;
import com.platform.order.vo.VipOrderVO;
import jakarta.servlet.http.HttpServletRequest;

public interface VipOrderService {
    
    /**
     * 创建VIP订阅订单
     */
    VipOrderVO createVipOrder(VipOrderDTO orderDTO, HttpServletRequest request);
    
    /**
     * 查询VIP订单
     */
    VipOrderVO getVipOrder(String orderId);
    
    /**
     * 更新订单状态
     */
    void updateOrderStatus(String orderId, String status);
}