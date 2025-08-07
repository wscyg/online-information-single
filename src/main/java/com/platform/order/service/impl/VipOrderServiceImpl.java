package com.platform.order.service.impl;

import com.platform.auth.util.JwtUtil;
import com.platform.order.dto.VipOrderDTO;
import com.platform.order.service.VipOrderService;
import com.platform.order.vo.VipOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class VipOrderServiceImpl implements VipOrderService {
    
    // 内存存储订单（生产环境应该用数据库）
    private final Map<String, VipOrderVO> orderCache = new ConcurrentHashMap<>();
    
    @Override
    public VipOrderVO createVipOrder(VipOrderDTO orderDTO, HttpServletRequest request) {
        log.info("创建VIP订单: {}", orderDTO);
        
        // 生成订单号
        String orderId = "VIP_" + System.currentTimeMillis();
        
        // 根据VIP类型确定价格
        BigDecimal price = calculatePrice(orderDTO.getVipType());
        
        // 创建订单
        VipOrderVO orderVO = new VipOrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setVipType(orderDTO.getVipType());
        orderVO.setPrice(price);
        orderVO.setStatus("PENDING");
        orderVO.setCreateTime(LocalDateTime.now());
        
        // 设置过期时间（30分钟）
        orderVO.setExpireTime(LocalDateTime.now().plusMinutes(30));
        
        // 存储订单
        orderCache.put(orderId, orderVO);
        
        log.info("VIP订单创建成功: {}", orderVO);
        return orderVO;
    }
    
    @Override
    public VipOrderVO getVipOrder(String orderId) {
        VipOrderVO order = orderCache.get(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在: " + orderId);
        }
        return order;
    }
    
    @Override
    public void updateOrderStatus(String orderId, String status) {
        VipOrderVO order = orderCache.get(orderId);
        if (order != null) {
            order.setStatus(status);
            log.info("订单状态更新: 订单号={}, 状态={}", orderId, status);
        }
    }
    
    private BigDecimal calculatePrice(String vipType) {
        switch (vipType) {
            case "monthly":
                return new BigDecimal("0.01"); // 月度会员 0.01元
            case "yearly":
                return new BigDecimal("0.02"); // 年度会员 0.02元
            case "lifetime":
                return new BigDecimal("0.02"); // 终身会员 0.02元
            default:
                return new BigDecimal("0.01");
        }
    }
}