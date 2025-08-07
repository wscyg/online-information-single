package com.platform.config;

import com.platform.auth.filter.JwtAuthenticationFilter;
import com.platform.auth.handler.CustomAuthenticationFailureHandler;
import com.platform.auth.handler.CustomAuthenticationSuccessHandler;
import com.platform.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security 安全配置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF
            .csrf(csrf -> csrf.disable())
            
            // 配置CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 配置Session管理
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 配置请求授权
            .authorizeHttpRequests(auth -> auth
                // 公开接口 (注意：context-path是/api，所以这里的路径不需要/api前缀)
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/captcha/**").permitAll()
                .requestMatchers("/content/public/**").permitAll()
                .requestMatchers("/content/columns/**").permitAll()
                .requestMatchers("/content/columns").permitAll()
                .requestMatchers("/content/health").permitAll()
                .requestMatchers("/content/secure/**").permitAll()
                .requestMatchers("/content/access-token").permitAll()
                .requestMatchers("/subscription/**").permitAll()  // 订阅管理接口（测试用）
                .requestMatchers("/payment/**").permitAll()  // 支付接口（包括支付页面和回调）
                
                // Swagger文档
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                
                // Druid监控
                .requestMatchers("/druid/**").permitAll()
                
                // 健康检查
                .requestMatchers("/actuator/health").permitAll()
                
                // 静态资源
                .requestMatchers("/static/**", "/public/**", "/uploads/**").permitAll()
                
                // 管理员接口
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // 其他接口需要认证
                .anyRequest().authenticated()
            )
            
            // 添加JWT过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            
            // 配置认证成功和失败处理器
            .formLogin(form -> form
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 允许的域名
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "http://localhost:3005",
            "http://localhost:3006",
            "http://localhost:5173", 
            "http://127.0.0.1:3000",
            "http://127.0.0.1:3005",
            "http://127.0.0.1:3006",
            "http://127.0.0.1:5173",
            "https://your-domain.com"
        ));
        
        // 允许的HTTP方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // 允许的请求头
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // 允许发送Cookie
        configuration.setAllowCredentials(true);
        
        // 预检请求缓存时间
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}