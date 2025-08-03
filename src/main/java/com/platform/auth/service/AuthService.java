package com.platform.auth.service;

import com.platform.auth.dto.*;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    LoginResponse loginByPhone(PhoneLoginRequest request);
    void sendSmsCode(SendSmsRequest request);
    LoginResponse register(RegisterRequest request);
    LoginResponse refreshToken(String refreshToken);
    void logout(String token);
    UserInfo getUserInfo(String token);
    void changePassword(ChangePasswordRequest request, String token);
    void resetPassword(ResetPasswordRequest request);
}