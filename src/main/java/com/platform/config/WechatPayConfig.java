package com.platform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置
 */
@Data
//@Component
@ConfigurationProperties(prefix = "wechatpay")
public class WechatPayConfig {
    private String appId;
    private String mchId;
    private String apiKey;
    private String certPath;
    private String notifyUrl;
    
    public String getAppId() {
        return appId;
    }
    
    public String getMchId() {
        return mchId;
    }
}