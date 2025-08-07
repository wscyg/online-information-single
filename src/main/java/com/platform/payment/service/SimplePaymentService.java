package com.platform.payment.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.platform.config.AlipayConfig;
import com.platform.payment.dto.PaymentDTO;
import com.platform.payment.vo.PaymentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class SimplePaymentService {
    
    private final AlipayConfig alipayConfig;
    private final AlipayClient alipayClient;
    
    public SimplePaymentService(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
        this.alipayClient = new DefaultAlipayClient(
            alipayConfig.getGatewayUrl(),
            alipayConfig.getAppId(),
            alipayConfig.getAppPrivateKey(),
            alipayConfig.getFormat(),
            alipayConfig.getCharset(),
            alipayConfig.getAlipayPublicKey(),
            alipayConfig.getSignType()
        );
        log.info("支付宝客户端初始化完成，AppId: {}", alipayConfig.getAppId());
    }
    
    public PaymentVO createPayment(PaymentDTO payment) {
        log.info("创建支付订单: {}", payment);
        
        PaymentVO vo = new PaymentVO();
        vo.setOrderNo(payment.getOrderNo());
        vo.setAmount(payment.getAmount());
        vo.setPayType(payment.getPayType() != null ? payment.getPayType() : "alipay");
        
        try {
            if ("alipay".equals(vo.getPayType())) {
                String payUrl = createAlipayPagePayment(payment);
                vo.setPayUrl(payUrl);
                log.info("支付宝支付URL生成成功: {}", payUrl.substring(0, Math.min(100, payUrl.length())));
            } else if ("wechat".equals(vo.getPayType())) {
                // 微信支付暂未实现
                throw new RuntimeException("微信支付暂未开放");
            } else {
                throw new RuntimeException("不支持的支付方式: " + vo.getPayType());
            }
        } catch (AlipayApiException e) {
            log.error("支付宝支付创建失败: {}", e.getMessage(), e);
            throw new RuntimeException("支付宝支付创建失败: " + e.getMessage(), e);
        }
        
        return vo;
    }
    
    private String createAlipayPagePayment(PaymentDTO payment) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        
        // 设置支付参数
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setBody(payment.getBody() != null ? payment.getBody() : "VIP会员订阅");
        model.setSubject(payment.getSubject() != null ? payment.getSubject() : "信息革命VIP会员");
        model.setOutTradeNo(payment.getOrderNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(payment.getAmount().toString());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        
        request.setBizModel(model);
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        request.setReturnUrl(alipayConfig.getReturnUrl());
        
        log.info("支付宝支付参数: 订单号={}, 金额={}, 商品={}", 
                payment.getOrderNo(), payment.getAmount(), model.getSubject());
        
        // 生成支付表单HTML
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        
        if (response.isSuccess()) {
            log.info("支付宝支付表单生成成功");
            return response.getBody();
        } else {
            log.error("支付宝支付表单生成失败: {}", response.getSubMsg());
            throw new AlipayApiException("支付表单生成失败: " + response.getSubMsg());
        }
    }
    
    public String queryPayment(String orderNo) {
        log.info("查询支付状态: {}", orderNo);
        
        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(orderNo);
            request.setBizModel(model);
            
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            
            if (response.isSuccess()) {
                String tradeStatus = response.getTradeStatus();
                log.info("支付状态查询成功: 订单={}, 状态={}", orderNo, tradeStatus);
                
                // 转换支付宝状态到标准状态
                switch (tradeStatus) {
                    case "TRADE_SUCCESS":
                    case "TRADE_FINISHED":
                        return "SUCCESS";
                    case "WAIT_BUYER_PAY":
                        return "PENDING";
                    case "TRADE_CLOSED":
                        return "CLOSED";
                    default:
                        return "UNKNOWN";
                }
            } else {
                log.warn("支付状态查询失败: 订单={}, 错误={}", orderNo, response.getSubMsg());
                return "UNKNOWN";
            }
        } catch (AlipayApiException e) {
            log.error("支付状态查询异常: 订单={}, 错误={}", orderNo, e.getMessage(), e);
            return "ERROR";
        }
    }
    
    public boolean refund(String orderNo, String amount) {
        log.info("发起退款: 订单={}, 金额={}", orderNo, amount);
        // 实际生产环境需要调用真实的退款接口
        // 这里暂时返回成功，实际需要实现退款逻辑
        return true;
    }
}