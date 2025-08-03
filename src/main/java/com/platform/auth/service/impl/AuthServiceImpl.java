package com.platform.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.auth.dto.*;
import com.platform.common.entity.User;
import com.platform.auth.mapper.AuthUserMapper;
import com.platform.auth.service.AuthService;
import com.platform.auth.util.JwtUtil;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    private static final String SMS_CODE_PREFIX = "sms:code:";
    private static final String USER_TOKEN_PREFIX = "user:token:";

    @Override
    public LoginResponse login(LoginRequest request) {
        // 查询用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername())
                .or()
                .eq(User::getPhone, request.getUsername())
                .or()
                .eq(User::getEmail, request.getUsername()));

        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }

        // Note: Login time tracking removed due to database schema mismatch

        // 生成token
        return generateTokenResponse(user);
    }

    @Override
    public LoginResponse loginByPhone(PhoneLoginRequest request) {
        // 验证短信验证码
        String cacheKey = SMS_CODE_PREFIX + request.getPhone();
        String cacheCode = (String) redisUtil.get(cacheKey);

        if (cacheCode == null || !cacheCode.equals(request.getCode())) {
            throw new BusinessException("验证码错误或已过期");
        }

        // 查询用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, request.getPhone()));

        if (user == null) {
            // 自动注册
            user = new User();
            user.setPhone(request.getPhone());
            user.setNickname("用户" + request.getPhone().substring(7));
            user.setStatus(1);
            userMapper.insert(user);
        }

        // 删除验证码
        redisUtil.del(cacheKey);

        // Note: Login time tracking removed due to database schema mismatch

        return generateTokenResponse(user);
    }

    @Override
    public void sendSmsCode(SendSmsRequest request) {
        String cacheKey = SMS_CODE_PREFIX + request.getPhone();
        System.out.println("发送验证码 - 手机号: " + request.getPhone());
        System.out.println("发送验证码 - 缓存Key: " + cacheKey);

        // 检查发送频率 (开发环境暂时关闭)
        boolean hasExisting = redisUtil.hasKey(cacheKey);
        System.out.println("发送验证码 - Redis中是否已存在: " + hasExisting);
        if (hasExisting) {
            String existingCode = (String) redisUtil.get(cacheKey);
            System.out.println("发送验证码 - 已存在的验证码: " + existingCode);
            throw new BusinessException("验证码已发送，请稍后再试");
        }

        // 生成验证码 (开发环境固定为123456)
        String code = "123456";

        // 发送短信（调用短信服务）
        // smsService.send(request.getPhone(), code);
        // 开发环境：打印验证码到控制台
        System.out.println("=== 开发环境验证码 ===");
        System.out.println("手机号: " + request.getPhone());
        System.out.println("验证码: " + code);
        System.out.println("缓存Key: " + cacheKey);
        System.out.println("==================");

        // 缓存验证码，5分钟有效
        try {
            redisUtil.set(cacheKey, code, 5, TimeUnit.MINUTES);
            System.out.println("验证码已存储到Redis: " + cacheKey + " -> " + code);
            
            // 验证存储是否成功
            String storedCode = (String) redisUtil.get(cacheKey);
            System.out.println("验证存储结果: " + storedCode);
        } catch (Exception e) {
            System.err.println("Redis存储失败: " + e.getMessage());
            e.printStackTrace();
            throw new BusinessException("系统异常，请稍后重试");
        }
    }

    @Override
    @Transactional
    public LoginResponse register(RegisterRequest request) {
        // 验证图片验证码 (开发环境临时禁用)
        if (!"test".equals(request.getCaptcha())) {
            String captchaKey = "captcha:" + request.getCaptchaId();
            System.out.println("注册验证 - 查找图片验证码 key: " + captchaKey);
            String cachedCaptcha = (String) redisUtil.get(captchaKey);
            System.out.println("注册验证 - 从Redis获取到的验证码: " + cachedCaptcha);
            System.out.println("注册验证 - 请求的验证码: " + request.getCaptcha());

            if (cachedCaptcha == null) {
                throw new BusinessException("验证码已过期，请重新获取");
            }
            
            if (!cachedCaptcha.equals(request.getCaptcha().toLowerCase())) {
                throw new BusinessException("验证码错误");
            }
            
            // 验证通过后立即删除验证码（防止重复使用）
            redisUtil.del(captchaKey);
        }

        // 检查用户名是否存在
        if (userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername())) > 0) {
            throw new BusinessException("用户名已存在");
        }

        // 检查手机号是否存在
        if (userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, request.getPhone())) > 0) {
            throw new BusinessException("手机号已注册");
        }

        // 创建用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname() != null ? request.getNickname() : request.getUsername());
        user.setStatus(1);
        userMapper.insert(user);

        // 处理邀请关系
        if (request.getInviteCode() != null) {
            // 处理推广逻辑
        }

        return generateTokenResponse(user);
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        if (userId == null) {
            throw new BusinessException("无效的刷新令牌");
        }

        User user = userMapper.selectById(userId);
        if (user == null || user.getStatus() != 1) {
            throw new BusinessException("用户不存在或已被禁用");
        }

        return generateTokenResponse(user);
    }

    @Override
    public void logout(String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        if (userId != null) {
            redisUtil.del(USER_TOKEN_PREFIX + userId);
        }
    }

    @Override
    public UserInfo getUserInfo(String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        if (userId == null) {
            throw new BusinessException("无效的访问令牌");
        }

        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);

        // 设置其他信息
        userInfo.setIsVip(false); // VIP功能暂未实现

        return userInfo;
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        User user = userMapper.selectById(userId);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException("原密码错误");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userMapper.updateById(user);

        // 清除token
        redisUtil.del(USER_TOKEN_PREFIX + userId);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        // 验证短信验证码
        String cacheKey = SMS_CODE_PREFIX + request.getPhone();
        String cacheCode = (String) redisUtil.get(cacheKey);

        if (cacheCode == null || !cacheCode.equals(request.getCode())) {
            throw new BusinessException("验证码错误或已过期");
        }

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, request.getPhone()));

        if (user == null) {
            throw new BusinessException("手机号未注册");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userMapper.updateById(user);

        // 删除验证码
        redisUtil.del(cacheKey);

        // 清除token
        redisUtil.del(USER_TOKEN_PREFIX + user.getId());
    }

    private LoginResponse generateTokenResponse(User user) {
        String accessToken = jwtUtil.generateToken(user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        // 缓存token
        redisUtil.set(USER_TOKEN_PREFIX + user.getId(), accessToken, 7, TimeUnit.DAYS);

        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setTokenType("Bearer");
        response.setExpiresIn(jwtUtil.getExpiration());

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        response.setUserInfo(userInfo);

        return response;
    }

    private String generateSmsCode() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }
}