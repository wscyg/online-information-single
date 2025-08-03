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
    public Result<String> createSubscription(@RequestBody java.util.Map<String, Object> request, HttpServletRequest httpRequest) {
        try {
            Long columnId = Long.valueOf(request.get("columnId").toString());
            String columnName = (String) request.get("columnName");
            String columnTitle = (String) request.get("columnTitle");
            
            // 从token获取用户ID
            Long userId = getUserIdFromRequest(httpRequest);
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            // 检查是否已经订阅
            if (subscriptionService.hasAccess(userId, columnId)) {
                return Result.success("用户已经订阅该专栏");
            }
            
            // 添加订阅
            subscriptionService.addSubscription(userId, columnId);
            
            return Result.success("订阅创建成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("订阅创建失败: " + e.getMessage());
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