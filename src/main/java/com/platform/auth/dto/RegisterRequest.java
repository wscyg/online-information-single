package com.platform.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "注册请求")
public class RegisterRequest {

    @Schema(description = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度必须在4-20之间")
    private String username;

    @Schema(description = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Schema(description = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    private String password;

    @Schema(description = "图片验证码", required = true)
    @NotBlank(message = "图片验证码不能为空")
    private String captcha;
    
    @Schema(description = "图片验证码ID", required = true)
    @NotBlank(message = "图片验证码ID不能为空")
    private String captchaId;
    
    @Schema(description = "短信验证码（暂未启用）")
    private String code;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "邀请码")
    private String inviteCode;

    @Schema(description = "来源")
    private String source = "web";

    @Schema(hidden = true)
    private String registerIp;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getCaptcha() { return captcha; }
    public void setCaptcha(String captcha) { this.captcha = captcha; }
    
    public String getCaptchaId() { return captchaId; }
    public void setCaptchaId(String captchaId) { this.captchaId = captchaId; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    
    public String getInviteCode() { return inviteCode; }
    public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public String getRegisterIp() { return registerIp; }
    public void setRegisterIp(String registerIp) { this.registerIp = registerIp; }
}