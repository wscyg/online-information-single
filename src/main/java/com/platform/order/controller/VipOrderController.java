package com.platform.order.controller;

import com.platform.common.result.Result;
import com.platform.order.dto.VipOrderDTO;
import com.platform.order.service.VipOrderService;
import com.platform.order.vo.VipOrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Tag(name = "VIP订单管理", description = "VIP会员订阅订单相关接口")
@RestController
@RequestMapping("/orders")
public class VipOrderController {
    
    @Autowired
    private VipOrderService vipOrderService;
    
    @Operation(summary = "创建VIP订阅订单")
    @PostMapping("/vip-subscribe")
    public Result<VipOrderVO> createVipOrder(@RequestBody VipOrderDTO orderDTO, HttpServletRequest request) {
        log.info("创建VIP订阅订单: {}", orderDTO);
        
        try {
            VipOrderVO orderVO = vipOrderService.createVipOrder(orderDTO, request);
            return Result.success(orderVO);
        } catch (Exception e) {
            log.error("创建VIP订阅订单失败: {}", e.getMessage(), e);
            return Result.error("创建订单失败: " + e.getMessage());
        }
    }
    
    @Operation(summary = "查询VIP订单状态")
    @GetMapping("/vip/{orderId}")
    public Result<VipOrderVO> getVipOrder(@PathVariable String orderId) {
        log.info("查询VIP订单状态: {}", orderId);
        
        try {
            VipOrderVO orderVO = vipOrderService.getVipOrder(orderId);
            return Result.success(orderVO);
        } catch (Exception e) {
            log.error("查询VIP订单失败: {}", e.getMessage(), e);
            return Result.error("查询订单失败: " + e.getMessage());
        }
    }
}