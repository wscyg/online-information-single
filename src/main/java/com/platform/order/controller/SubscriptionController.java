package com.platform.order.controller;

import com.platform.auth.util.JwtUtil;
import com.platform.common.result.Result;
import com.platform.order.service.impl.SubscriptionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "订阅管理", description = "订阅相关接口")
@RestController
@RequestMapping("/subscription")
@CrossOrigin
public class SubscriptionController {
    
    @Autowired
    private SubscriptionServiceImpl subscriptionService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Operation(summary = "检查用户订阅状态")
    @GetMapping("/check/{columnId}")
    public Result<Boolean> checkSubscription(@PathVariable Long columnId, HttpServletRequest request) {
        try {
            // 从token获取用户ID
            Long userId = getUserIdFromRequest(request);
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            boolean hasAccess = subscriptionService.hasAccess(userId, columnId);
            return Result.success(hasAccess);
        } catch (Exception e) {
            return Result.error("检查订阅状态失败：" + e.getMessage());
        }
    }
    
    @Operation(summary = "添加订阅（测试用）")
    @PostMapping("/add")
    public Result<String> addSubscription(@RequestParam Long columnId, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            subscriptionService.addSubscription(userId, columnId);
            return Result.success("订阅添加成功");
        } catch (Exception e) {
            return Result.error("添加订阅失败：" + e.getMessage());
        }
    }
    
    @Operation(summary = "移除订阅（测试用）")
    @DeleteMapping("/remove")
    public Result<String> removeSubscription(@RequestParam Long columnId, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            subscriptionService.removeSubscription(userId, columnId);
            return Result.success("订阅移除成功");
        } catch (Exception e) {
            return Result.error("移除订阅失败：" + e.getMessage());
        }
    }
    
    @Operation(summary = "为指定用户添加订阅（管理员用）")
    @PostMapping("/admin/add")
    public Result<String> addSubscriptionForUser(@RequestParam Long userId, @RequestParam Long columnId) {
        try {
            subscriptionService.addSubscription(userId, columnId);
            return Result.success("为用户 " + userId + " 添加专栏 " + columnId + " 订阅成功");
        } catch (Exception e) {
            return Result.error("添加订阅失败：" + e.getMessage());
        }
    }
    
    @Operation(summary = "创建专栏订阅")
    @PostMapping("/create")
    public Result<Object> createSubscription(@RequestBody java.util.Map<String, Object> request, HttpServletRequest httpRequest) {
        try {
            Long columnId = Long.valueOf(request.get("columnId").toString());
            String columnName = (String) request.get("columnName");
            String columnTitle = (String) request.get("columnTitle");
            
            // 从token获取用户ID
            Long userId = getUserIdFromRequest(httpRequest);
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            // 检查是否已经有付费订阅（直接查数据库，不使用缓存）
            java.util.List<com.platform.order.entity.ColumnSubscription> userSubscriptions = subscriptionService.getUserSubscriptions(userId);
            boolean alreadySubscribed = userSubscriptions.stream()
                .anyMatch(sub -> sub.getColumnId().equals(columnId));
            
            if (alreadySubscribed) {
                System.out.println("✅ 用户 " + userId + " 已经订阅专栏 " + columnId);
                return Result.success("用户已经订阅该专栏");
            }
            
            System.out.println("🔄 用户 " + userId + " 需要订阅专栏 " + columnId + "，创建支付订单");
            // 创建支付订单（而不是直接添加订阅）
            return createPaymentOrderForColumn(columnId, columnName, columnTitle, userId);
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("订阅创建失败: " + e.getMessage());
        }
    }
    
    private Result<Object> createPaymentOrderForColumn(Long columnId, String columnName, String columnTitle, Long userId) {
        try {
            // 生成订单号
            String orderId = "COLUMN_" + columnId + "_" + System.currentTimeMillis();
            
            // 根据专栏确定价格（单位：元）
            double price = getColumnPrice(columnId);
            
            // 构建支付宝支付链接
            String alipayUrl = String.format(
                "http://42.194.245.66/single/payment/alipay/pay?subject=%s&totalAmount=%.2f&outTradeNo=%s",
                java.net.URLEncoder.encode(columnTitle, "UTF-8"),
                price,
                orderId
            );
            
            // 返回支付信息
            final String finalPaymentUrl = alipayUrl;
            final String finalOrderId = orderId;
            final double finalPrice = price;
            final String finalColumnTitle = columnTitle;
            
            return Result.success(new Object() {
                public String paymentUrl = finalPaymentUrl;
                public String orderId = finalOrderId;
                public double amount = finalPrice;
                public String columnTitle = finalColumnTitle;
                public String message = "请完成支付以订阅专栏";
                public String paymentType = "alipay";
                public Object paymentInfo = new Object() {
                    public String qrCode = finalPaymentUrl; // 前端可以生成二维码
                    public String directUrl = finalPaymentUrl; // 直接跳转链接
                };
            });
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("创建支付订单失败: " + e.getMessage());
        }
    }
    
    private double getColumnPrice(Long columnId) {
        // 根据专栏ID返回价格（单位：元）
        switch (columnId.intValue()) {
            case 1: // transformer
                return 0.01;
            case 2: // 机器学习
                return 0.01;
            case 3: // 深度学习
                return 0.01;
            case 4: // NLP
                return 0.01;
            case 100: // AI全套专栏
                return 0.02;
            default:
                return 0.01;
        }
    }
    
    @Operation(summary = "获取用户订阅列表")
    @GetMapping("/user-subscriptions")
    public Result<java.util.List<com.platform.order.entity.ColumnSubscription>> getUserSubscriptions(HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            java.util.List<com.platform.order.entity.ColumnSubscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
            return Result.success(subscriptions);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取用户订阅列表失败: " + e.getMessage());
        }
    }
    
    @Operation(summary = "初始化测试用户订阅")
    @PostMapping("/init-test")
    public Result<String> initializeTestSubscriptions() {
        try {
            subscriptionService.initializeTestSubscriptions();
            return Result.success("测试用户订阅初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("初始化失败: " + e.getMessage());
        }
    }
    
    private Long getUserIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                return jwtUtil.getUserIdFromToken(token);
            }
        }
        return null;
    }
}