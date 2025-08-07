package com.platform.payment.service;

import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.platform.config.AlipayConfig;
import com.platform.payment.dto.PaymentRequest;
import com.platform.payment.vo.PaymentResponse;
import com.platform.order.entity.ColumnSubscription;
import com.platform.order.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlipayService {
    
    private static final Logger log = LoggerFactory.getLogger(AlipayService.class);
    
    private final AlipayConfig alipayConfig;
    private final AlipayClient alipayClient;
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    public AlipayService(AlipayConfig alipayConfig) {
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
    }
    
    /**
     * APP支付
     */
    public PaymentResponse createAppPayment(PaymentRequest request) {
        try {
            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
            
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(request.getBody() != null ? request.getBody() : request.getSubject());
            model.setSubject(request.getSubject());
            model.setOutTradeNo(request.getOrderNo());
            model.setTimeoutExpress("30m");
            model.setTotalAmount(request.getAmount().toString());
            model.setProductCode("QUICK_MSECURITY_PAY");
            model.setPassbackParams(request.getPassbackParams());
            
            alipayRequest.setBizModel(model);
            alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
            
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);
            
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setOrderNo(request.getOrderNo());
            paymentResponse.setAmount(request.getAmount());
            paymentResponse.setPayType("alipay_app");
            
            if (response.isSuccess()) {
                paymentResponse.setPayUrl(response.getBody());
                paymentResponse.setStatus("SUCCESS");
                paymentResponse.setMessage("支付订单创建成功");
                log.info("APP支付订单创建成功，订单号：{}", request.getOrderNo());
            } else {
                paymentResponse.setStatus("FAILED");
                paymentResponse.setMessage("支付订单创建失败：" + response.getMsg());
                log.error("APP支付订单创建失败，订单号：{}，错误信息：{}", request.getOrderNo(), response.getMsg());
            }
            
            return paymentResponse;
            
        } catch (AlipayApiException e) {
            log.error("APP支付订单创建异常，订单号：{}", request.getOrderNo(), e);
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setOrderNo(request.getOrderNo());
            paymentResponse.setAmount(request.getAmount());
            paymentResponse.setPayType("alipay_app");
            paymentResponse.setStatus("FAILED");
            paymentResponse.setMessage("支付订单创建异常：" + e.getMessage());
            return paymentResponse;
        }
    }
    
    /**
     * PC网站支付
     */
    public PaymentResponse createPagePayment(PaymentRequest request) {
        try {
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setBody(request.getBody() != null ? request.getBody() : request.getSubject());
            model.setSubject(request.getSubject());
            model.setOutTradeNo(request.getOrderNo());
            model.setTimeoutExpress("30m");
            model.setTotalAmount(request.getAmount().toString());
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            
            alipayRequest.setBizModel(model);
            alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
            alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
            
            AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest);
            
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setOrderNo(request.getOrderNo());
            paymentResponse.setAmount(request.getAmount());
            paymentResponse.setPayType("alipay_page");
            
            if (response.isSuccess()) {
                paymentResponse.setPayUrl(response.getBody());
                paymentResponse.setStatus("SUCCESS");
                paymentResponse.setMessage("PC网站支付订单创建成功");
                log.info("PC网站支付订单创建成功，订单号：{}", request.getOrderNo());
            } else {
                paymentResponse.setStatus("FAILED");
                paymentResponse.setMessage("PC网站支付订单创建失败：" + response.getMsg());
                log.error("PC网站支付订单创建失败，订单号：{}，错误信息：{}", request.getOrderNo(), response.getMsg());
            }
            
            return paymentResponse;
            
        } catch (AlipayApiException e) {
            log.error("PC网站支付订单创建异常，订单号：{}", request.getOrderNo(), e);
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setOrderNo(request.getOrderNo());
            paymentResponse.setAmount(request.getAmount());
            paymentResponse.setPayType("alipay_page");
            paymentResponse.setStatus("FAILED");
            paymentResponse.setMessage("PC网站支付订单创建异常：" + e.getMessage());
            return paymentResponse;
        }
    }
    
    /**
     * 扫码支付（PC端）
     */
    public PaymentResponse createQrCodePayment(PaymentRequest request) {
        try {
            AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();
            
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
            model.setBody(request.getBody() != null ? request.getBody() : request.getSubject());
            model.setSubject(request.getSubject());
            model.setOutTradeNo(request.getOrderNo());
            model.setTimeoutExpress("30m");
            model.setTotalAmount(request.getAmount().toString());
            
            alipayRequest.setBizModel(model);
            alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
            
            AlipayTradePrecreateResponse response = alipayClient.execute(alipayRequest);
            
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setOrderNo(request.getOrderNo());
            paymentResponse.setAmount(request.getAmount());
            paymentResponse.setPayType("alipay_qr");
            
            if (response.isSuccess()) {
                paymentResponse.setQrCodeUrl(response.getQrCode());
                paymentResponse.setStatus("SUCCESS");
                paymentResponse.setMessage("二维码支付订单创建成功");
                log.info("二维码支付订单创建成功，订单号：{}", request.getOrderNo());
            } else {
                paymentResponse.setStatus("FAILED");
                paymentResponse.setMessage("二维码支付订单创建失败：" + response.getMsg());
                log.error("二维码支付订单创建失败，订单号：{}，错误信息：{}", request.getOrderNo(), response.getMsg());
            }
            
            return paymentResponse;
            
        } catch (AlipayApiException e) {
            log.error("二维码支付订单创建异常，订单号：{}", request.getOrderNo(), e);
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setOrderNo(request.getOrderNo());
            paymentResponse.setAmount(request.getAmount());
            paymentResponse.setPayType("alipay_qr");
            paymentResponse.setStatus("FAILED");
            paymentResponse.setMessage("二维码支付订单创建异常：" + e.getMessage());
            return paymentResponse;
        }
    }
    
    /**
     * 查询支付状态
     */
    public String queryPaymentStatus(String orderNo) {
        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(orderNo);
            
            request.setBizModel(model);
            
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            
            if (response.isSuccess()) {
                String tradeStatus = response.getTradeStatus();
                log.info("查询支付状态成功，订单号：{}，状态：{}", orderNo, tradeStatus);
                return tradeStatus;
            } else {
                log.error("查询支付状态失败，订单号：{}，错误信息：{}", orderNo, response.getMsg());
                return "QUERY_FAILED";
            }
            
        } catch (AlipayApiException e) {
            log.error("查询支付状态异常，订单号：{}", orderNo, e);
            return "QUERY_ERROR";
        }
    }
    
    /**
     * 验证回调通知
     */
    public boolean verifyNotification(HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            
            for (String name : requestParams.keySet()) {
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            
            boolean signVerified = com.alipay.api.internal.util.AlipaySignature.rsaCheckV1(
                params, 
                alipayConfig.getAlipayPublicKey(), 
                alipayConfig.getCharset(),
                alipayConfig.getSignType()
            );
            
            if (signVerified) {
                String tradeStatus = params.get("trade_status");
                String outTradeNo = params.get("out_trade_no");
                
                log.info("支付宝回调验证成功，订单号：{}，交易状态：{}", outTradeNo, tradeStatus);
                log.info("回调参数：{}", JSON.toJSONString(params));
                
                // 处理支付成功的业务逻辑
                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                    handlePaymentSuccess(outTradeNo, params);
                }
                
                return true;
            } else {
                log.error("支付宝回调验证失败");
                return false;
            }
            
        } catch (Exception e) {
            log.error("支付宝回调验证异常", e);
            return false;
        }
    }
    
    /**
     * 处理支付成功的业务逻辑
     */
    private void handlePaymentSuccess(String orderNo, Map<String, String> params) {
        try {
            log.info("处理支付成功业务逻辑，订单号：{}", orderNo);
            
            // 更新订阅状态为已支付
            ColumnSubscription subscription = subscriptionService.findByOrderNo(orderNo);
            if (subscription != null) {
                subscription.setPayStatus("SUCCESS");
                subscription.setStatus("ACTIVE");
                subscriptionService.updateSubscription(subscription);
                log.info("订阅状态更新成功，订单号：{}，用户ID：{}", orderNo, subscription.getUserId());
            } else {
                log.warn("未找到对应的订阅记录，订单号：{}", orderNo);
            }
            
        } catch (Exception e) {
            log.error("处理支付成功业务逻辑异常，订单号：{}", orderNo, e);
        }
    }
}