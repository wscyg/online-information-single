package com.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Web配置类，用于控制静态资源访问
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加AI-content拦截器，阻止直接访问
        registry.addInterceptor(new AIContentInterceptor())
                .addPathPatterns("/AI-content/**");
    }
    
    /**
     * AI内容访问拦截器
     */
    public static class AIContentInterceptor implements HandlerInterceptor {
        
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            // 拦截所有对AI-content目录的直接访问
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":403,\"message\":\"直接访问被禁止，请通过课程大纲访问内容\"}");
            return false;
        }
    }
}