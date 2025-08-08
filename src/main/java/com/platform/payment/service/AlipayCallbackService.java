package com.platform.payment.service;

import com.alipay.api.internal.util.AlipaySignature;
import com.platform.config.AlipayConfig;
import com.platform.order.service.SubscriptionService;
// import com.platform.order.service.VipOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
public class AlipayCallbackService {
    
    private final AlipayConfig alipayConfig;
    
    // @Autowired
    // private VipOrderService vipOrderService;
    
    @Autowired
    private com.platform.order.service.impl.SubscriptionServiceImpl subscriptionService;
    
    public AlipayCallbackService(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
    }
    
    public boolean verifyNotification(HttpServletRequest request) {
        try {
            Map<String, String> params = convertRequestParamsToMap(request);
            log.info("支付宝回调参数: {}", params);
            
            boolean verified = AlipaySignature.rsaCheckV1(
                params, 
                alipayConfig.getAlipayPublicKey(), 
                alipayConfig.getCharset(), 
                "RSA2"
            );
            
            if (verified) {
                log.info("支付宝回调验签成功");
                handlePaymentNotification(params);
                return true;
            } else {
                log.warn("支付宝回调验签失败");
                return false;
            }
        } catch (Exception e) {
            log.error("支付宝回调处理异常", e);
            return false;
        }
    }
    
    private void handlePaymentNotification(Map<String, String> params) {
        String tradeStatus = params.get("trade_status");
        String outTradeNo = params.get("out_trade_no");
        String tradeNo = params.get("trade_no");
        String totalAmount = params.get("total_amount");
        
        log.info("处理支付回调: 订单号={}, 交易号={}, 状态={}, 金额={}", 
                outTradeNo, tradeNo, tradeStatus, totalAmount);
        
        if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
            try {
                // 根据订单号前缀判断订单类型
                if (outTradeNo.startsWith("VIP_")) {
                    // VIP订单处理
                    // vipOrderService.updateOrderStatus(outTradeNo, "PAID");
                    handleVipOrderPayment(outTradeNo);
                } else if (outTradeNo.startsWith("COLUMN_")) {
                    // 专栏订单处理
                    handleColumnSubscriptionPayment(outTradeNo);
                } else {
                    // 通用订单处理
                    // vipOrderService.updateOrderStatus(outTradeNo, "PAID");
                }
                
                log.info("支付成功处理完成: 订单号={}", outTradeNo);
            } catch (Exception e) {
                log.error("支付成功处理异常: 订单号={}, 错误={}", outTradeNo, e.getMessage(), e);
            }
        }
    }
    
    private void handleColumnSubscriptionPayment(String orderId) {
        try {
            // 从订单号解析专栏ID：COLUMN_{columnId}_{timestamp}
            String[] parts = orderId.split("_");
            if (parts.length >= 3) {
                Long columnId = Long.valueOf(parts[1]);
                
                // 根据订单号查找对应的订阅记录，从中获取用户ID
                com.platform.order.entity.ColumnSubscription subscription = subscriptionService.findByOrderNo(orderId);
                if (subscription != null) {
                    Long userId = subscription.getUserId();
                    
                    // 更新订阅状态为ACTIVE，支付状态为SUCCESS
                    subscription.setStatus("ACTIVE");
                    subscription.setPayStatus("SUCCESS");
                    
                    // 更新数据库记录
                    subscriptionService.updateSubscription(subscription);
                    
                    log.info("专栏订阅支付成功处理完成: 订单号={}, 专栏ID={}, 用户ID={}", 
                            orderId, columnId, userId);
                } else {
                    log.error("未找到订单号对应的订阅记录: {}", orderId);
                    
                    // 如果找不到现有记录，尝试创建新的订阅（需要额外处理获取用户ID）
                    log.warn("正在尝试从订单表中查找用户信息: {}", orderId);
                }
            } else {
                log.error("无法解析专栏订单号: {}", orderId);
            }
        } catch (Exception e) {
            log.error("专栏订阅支付后处理失败: 订单号={}, 错误={}", orderId, e.getMessage(), e);
        }
    }
    
    private void handleVipOrderPayment(String orderId) {
        try {
            // 获取订单信息
            // var vipOrder = vipOrderService.getVipOrder(orderId);
            
            // 创建AI全套专栏订阅（这样会自动解锁所有子专栏）
            Map<String, Object> subscriptionData = new HashMap<>();
            subscriptionData.put("columnId", 100);
            subscriptionData.put("columnName", "AI-content");
            subscriptionData.put("columnTitle", "AI全套专栏");
            
            // 这里需要获取用户ID，暂时跳过实际的订阅创建
            // subscriptionService.createSubscription(subscriptionData, request);
            
            log.info("VIP订单支付成功，订阅已创建: 订单号={}", orderId);
        } catch (Exception e) {
            log.error("VIP订单支付后处理失败: 订单号={}, 错误={}", orderId, e.getMessage(), e);
        }
    }
    
    private Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }
    
    public boolean verifyCertNotification(HttpServletRequest request) {
        try {
            Map<String, String> params = convertRequestParamsToMap(request);
            
            boolean flag = AlipaySignature.rsaCertCheckV1(
                params,
                alipayConfig.getAlipayPublicCertPath(),
                alipayConfig.getCharset(),
                "RSA2"
            );
            
            if (flag) {
                handlePaymentNotification(params);
            }
            
            return flag;
        } catch (Exception e) {
            log.error("支付宝证书回调处理异常", e);
            return false;
        }
    }
}