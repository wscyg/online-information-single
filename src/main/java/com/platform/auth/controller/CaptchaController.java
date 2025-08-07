package com.platform.auth.controller;

import com.platform.auth.util.CaptchaUtil;
import com.platform.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码控制器
 */
@Tag(name = "验证码管理", description = "图片验证码相关接口")
@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    
    @Autowired
    private CaptchaUtil captchaUtil;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    private static final String CAPTCHA_KEY_PREFIX = "captcha:";
    private static final int CAPTCHA_EXPIRE_MINUTES = 5; // 验证码5分钟过期
    
    /**
     * 生成图片验证码
     */
    @Operation(summary = "生成图片验证码", description = "生成用于注册的图片验证码")
    @GetMapping("/generate")
    public ResponseEntity<Result<Map<String, String>>> generateCaptcha(HttpServletRequest request) {
        try {
            // 生成验证码
            CaptchaUtil.CaptchaResult captchaResult = captchaUtil.generateCaptcha();
            
            // 生成唯一标识
            String captchaId = UUID.randomUUID().toString();
            
            // 存储验证码到Redis（验证码统一转为小写存储，验证时忽略大小写）
            String redisKey = CAPTCHA_KEY_PREFIX + captchaId;
            redisTemplate.opsForValue().set(redisKey, captchaResult.getCode().toLowerCase(), 
                    CAPTCHA_EXPIRE_MINUTES, TimeUnit.MINUTES);
            
            // 返回结果
            Map<String, String> result = new HashMap<>();
            result.put("captchaId", captchaId);
            result.put("captchaImage", captchaResult.getImage());
            
            // 设置缓存控制头
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(Result.success(result));
            
        } catch (Exception e) {
            return ResponseEntity.ok(Result.error("生成验证码失败"));
        }
    }
    
    /**
     * 验证图片验证码
     */
    @Operation(summary = "验证图片验证码", description = "验证用户输入的图片验证码是否正确")
    @PostMapping("/verify")
    public Result<Boolean> verifyCaptcha(@RequestBody Map<String, String> request) {
        try {
            String captchaId = request.get("captchaId");
            String userInput = request.get("captcha");
            
            if (captchaId == null || userInput == null) {
                return Result.error("参数不能为空");
            }
            
            // 从Redis获取正确的验证码
            String redisKey = CAPTCHA_KEY_PREFIX + captchaId;
            String correctCode = (String) redisTemplate.opsForValue().get(redisKey);
            
            if (correctCode == null) {
                return Result.error("验证码已过期");
            }
            
            // 验证码验证（忽略大小写）
            boolean isValid = correctCode.equals(userInput.toLowerCase());
            
            if (isValid) {
                // 验证成功后删除验证码（防止重复使用）
                redisTemplate.delete(redisKey);
                return Result.success(true);
            } else {
                return Result.error("验证码错误");
            }
            
        } catch (Exception e) {
            return Result.error("验证失败");
        }
    }
}