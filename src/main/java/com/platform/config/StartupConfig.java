package com.platform.config;

import com.platform.order.service.impl.SubscriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupConfig implements CommandLineRunner {
    
    @Autowired
    private SubscriptionServiceImpl subscriptionService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 应用启动，执行初始化任务...");
        
        try {
            // 初始化测试用户订阅
            subscriptionService.initializeTestSubscriptions();
            System.out.println("✅ 初始化任务完成");
        } catch (Exception e) {
            System.out.println("❌ 初始化任务失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}