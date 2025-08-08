package com.platform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    private String appId;
    private String appPrivateKey;
    private String alipayPublicKey;
    private String signType = "RSA2";
    private String charset = "utf-8";
    private String format = "json";
    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";
    private String notifyUrl;
    private String returnUrl;
    private String appCertPath;
    private String alipayCertPath;
    private String alipayPublicCertPath;
    private String alipayRootCertPath;
    
    public String getAppPrivateKey() {
        return appPrivateKey;
    }
    
    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }
    
    public String getAlipayPublicCertPath() {
        return alipayPublicCertPath;
    }
    
    public String getGatewayUrl() {
        return gatewayUrl;
    }
    
    public String getAppId() {
        return appId;
    }
    
    public String getFormat() {
        return format;
    }
    
    public String getCharset() {
        return charset;
    }
    
    public String getSignType() {
        return signType;
    }
    
    public String getNotifyUrl() {
        return notifyUrl;
    }
    
    public String getReturnUrl() {
        return returnUrl;
    }
}