package com.platform.order.controller;

import com.platform.common.result.Result;
import com.platform.order.dto.CreateOrderDTO;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "订单管理", description = "订单相关接口")
@RestController
@RequestMapping("/order")
@CrossOrigin
public class SimpleOrderController {
    
    @Operation(summary = "创建订单")
    @PostMapping("/orders/create")
    public Result<String> createOrder(@RequestBody CreateOrderDTO dto) {
        // 简化的订单创建逻辑
        String orderNo = "ORDER_" + System.currentTimeMillis();
        return Result.success(orderNo);
    }
    
    @Operation(summary = "查询订单")
    @GetMapping("/orders/{orderNo}")
    public Result<String> getOrder(@PathVariable String orderNo) {
        return Result.success("Order status: PENDING");
    }
    
    @Operation(summary = "健康检查")
    @GetMapping("/orders/health")
    public Result<String> health() {
        return Result.success("Order service is running");
    }
    
    @Operation(summary = "获取用户订单列表")
    @GetMapping("/order/list")
    public Result<Object> getOrderList() {
        // 模拟订单数据
        return Result.success(new Object() {
            public Object[] records = new Object[] {
                new Object() {
                    public String orderNo = "ORDER_1753698400000";
                    public String productName = "张三的AI完整学习路径";
                    public Double totalAmount = 0.00;
                    public String payStatus = "PAID";
                    public String orderStatus = "SUCCESS";
                    public String createTime = "2024-01-15 10:30:00";
                },
                new Object() {
                    public String orderNo = "ORDER_1753698500000";
                    public String productName = "深度学习实战指南";
                    public Double totalAmount = 99.00;
                    public String payStatus = "PAID";
                    public String orderStatus = "SUCCESS";
                    public String createTime = "2024-01-14 15:20:00";
                }
            };
            public Integer total = 2;
            public Integer size = 10;
            public Integer current = 1;
        });
    }
    
    @Operation(summary = "简化订阅接口")
    @PostMapping("/order/subscribe")
    public Result<Object> subscribe(@RequestBody Object subscribeRequest) {
        try {
            // 临时实现：直接返回订阅成功
            // 实际项目中这里会创建订单记录和订阅记录
            final String orderNumber = "SUB_" + System.currentTimeMillis();
            final String subId = "SUBSCRIPTION_" + System.currentTimeMillis();
            
            return Result.success(new Object() {
                public String orderNo = orderNumber;
                public String status = "SUCCESS";
                public String message = "订阅成功";
                public String subscriptionId = subId;
            });
        } catch (Exception e) {
            return Result.error("订阅失败：" + e.getMessage());
        }
    }
}