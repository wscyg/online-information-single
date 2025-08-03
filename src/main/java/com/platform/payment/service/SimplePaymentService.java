package com.platform.payment.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.platform.config.AlipayConfig;
import com.platform.payment.dto.PaymentDTO;
import com.platform.payment.vo.PaymentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class SimplePaymentService {
    
    private final AlipayConfig alipayConfig;
    private final AlipayClient alipayClient;
    
    public SimplePaymentService(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
        this.alipayClient = new DefaultAlipayClient(
            "https://openapi.alipay.com/gateway.do",
            alipayConfig.getAppId(),
            alipayConfig.getAppPrivateKey(),
            "json",
            alipayConfig.getCharset(),
            alipayConfig.getAlipayPublicKey(),
            "RSA2"
        );
    }
    
    public PaymentVO createPayment(PaymentDTO payment) {
        PaymentVO vo = new PaymentVO();
        vo.setOrderNo(payment.getOrderNo());
        vo.setAmount(payment.getAmount());
        vo.setPayType("alipay");
        
        try {
            String orderString = createAlipayAppPayment(payment);
            vo.setPayUrl(orderString);
        } catch (AlipayApiException e) {
            throw new RuntimeException("支付宝支付创建失败: " + e.getMessage(), e);
        }
        
        return vo;
    }
    
    private String createAlipayAppPayment(PaymentDTO payment) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(payment.getBody() != null ? payment.getBody() : "支付订单");
        model.setSubject(payment.getSubject() != null ? payment.getSubject() : "App支付");
        model.setOutTradeNo(payment.getOrderNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(payment.getAmount().toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        
        request.setBizModel(model);
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        return response.getBody();
    }
    
    
    public String queryPayment(String orderNo) {
        // 模拟查询支付状态
        // 实际生产环境需要调用真实的支付宝或微信支付查询接口
        return "SUCCESS";
    }
    
    public boolean refund(String orderNo, String amount) {
        // 模拟退款操作
        // 实际生产环境需要调用真实的退款接口
        return true;
    }
}