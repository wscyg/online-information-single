package com.platform.auth.controller;

import com.platform.auth.dto.*;
import com.platform.auth.service.AuthService;
import com.platform.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "认证管理", description = "用户认证授权相关接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "用户名密码登录", description = "通过用户名和密码进行登录认证")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登录成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "401", description = "用户名或密码错误")
    })
    @PostMapping("/login")
    public Result<LoginResponse> login(@Validated @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        request.setLoginIp(getClientIp(httpRequest));
        return Result.success(authService.login(request));
    }

    @Operation(summary = "手机号登录", description = "通过手机号和验证码进行登录")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登录成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "401", description = "验证码错误")
    })
    @PostMapping("/login/phone")
    public Result<LoginResponse> loginByPhone(@Validated @RequestBody PhoneLoginRequest request, HttpServletRequest httpRequest) {
        request.setLoginIp(getClientIp(httpRequest));
        return Result.success(authService.loginByPhone(request));
    }

    @Operation(summary = "发送短信验证码", description = "向指定手机号发送短信验证码")
    @PostMapping("/sms/send")
    public Result<Void> sendSmsCode(@Validated @RequestBody SendSmsRequest request) {
        authService.sendSmsCode(request);
        return Result.success();
    }

    @Operation(summary = "用户注册", description = "新用户注册")
    @PostMapping("/register")
    public Result<LoginResponse> register(@Validated @RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
        request.setRegisterIp(getClientIp(httpRequest));
        return Result.success(authService.register(request));
    }

    @Operation(summary = "刷新Token", description = "使用刷新令牌获取新的访问令牌")
    @PostMapping("/refresh")
    public Result<LoginResponse> refreshToken(@RequestParam String refreshToken) {
        return Result.success(authService.refreshToken(refreshToken));
    }

    @Operation(summary = "用户登出", description = "用户退出登录")
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Result.success();
    }

    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的详细信息")
    @GetMapping("/user/info")
    public Result<UserInfo> getUserInfo(@RequestHeader("Authorization") String token) {
        return Result.success(authService.getUserInfo(token));
    }

    @Operation(summary = "修改密码", description = "用户修改登录密码")
    @PostMapping("/password/change")
    public Result<Void> changePassword(@Validated @RequestBody ChangePasswordRequest request,
                                       @RequestHeader("Authorization") String token) {
        authService.changePassword(request, token);
        return Result.success();
    }

    @Operation(summary = "重置密码", description = "通过短信验证码重置密码")
    @PostMapping("/password/reset")
    public Result<Void> resetPassword(@Validated @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return Result.success();
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}