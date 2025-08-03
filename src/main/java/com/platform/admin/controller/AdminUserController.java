package com.platform.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.dto.UserQueryDTO;
import com.platform.admin.service.AdminUserService;
import com.platform.admin.vo.UserListVO;
import com.platform.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "后台用户管理")
@RestController
@RequestMapping("/admin/user")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;
    
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @Operation(summary = "用户列表")
    @GetMapping("/list")
    public Result<IPage<UserListVO>> list(UserQueryDTO query) {
        return Result.success(adminUserService.getUserList(query));
    }

    @Operation(summary = "禁用用户")
    @PostMapping("/{userId}/disable")
    public Result<?> disable(@PathVariable Long userId) {
        adminUserService.disableUser(userId);
        return Result.success();
    }

    @Operation(summary = "启用用户")
    @PostMapping("/{userId}/enable")
    public Result<?> enable(@PathVariable Long userId) {
        adminUserService.enableUser(userId);
        return Result.success();
    }

    @Operation(summary = "重置密码")
    @PostMapping("/{userId}/reset-password")
    public Result<String> resetPassword(@PathVariable Long userId) {
        String newPassword = adminUserService.resetPassword(userId);
        return Result.success(newPassword);
    }
}