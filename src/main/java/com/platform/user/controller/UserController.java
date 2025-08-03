package com.platform.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.common.result.Result;
import com.platform.common.entity.User;
import com.platform.user.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    
    @Resource
    private UserService userService;
    
    @GetMapping("/test")
    public String test() {
        return "User service is running!";
    }
    
    @PostMapping("/register")
    public Result<String> register(@RequestBody @Validated User user) {
        boolean success = userService.register(user);
        if (success) {
            return Result.success("注册成功");
        }
        return Result.error("注册失败，用户名或邮箱已存在");
    }
    
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword(null); // 不返回密码
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }
    
    @GetMapping("/username/{username}")
    public Result<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            user.setPassword(null); // 不返回密码
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }
    
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        user.setPassword(null); // 不允许通过此接口修改密码
        boolean success = userService.updateById(user);
        if (success) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
    
    @PostMapping("/{id}/password")
    public Result<String> updatePassword(@PathVariable Long id, 
                                       @RequestParam String oldPassword,
                                       @RequestParam String newPassword) {
        boolean success = userService.updatePassword(id, oldPassword, newPassword);
        if (success) {
            return Result.success("密码修改成功");
        }
        return Result.error("密码修改失败，请检查原密码");
    }
    
    @GetMapping("/page")
    public Result<IPage<User>> pageQuery(@RequestParam(defaultValue = "1") int pageNum,
                                       @RequestParam(defaultValue = "10") int pageSize,
                                       @RequestParam(required = false) String keyword) {
        IPage<User> page = userService.pageQuery(pageNum, pageSize, keyword);
        // 清除密码信息
        page.getRecords().forEach(user -> user.setPassword(null));
        return Result.success(page);
    }
    
    @GetMapping("/progress")
    public Result<java.util.Map<String, Integer>> getUserProgress() {
        // 返回前端期望的进度数据格式：columnId -> progress percentage
        java.util.Map<String, Integer> progressData = new java.util.HashMap<>();
        progressData.put("1", 75);  // 专栏1的进度75%
        progressData.put("2", 50);  // 专栏2的进度50%
        progressData.put("3", 0);   // 专栏3的进度0%
        
        return Result.success(progressData);
    }
}