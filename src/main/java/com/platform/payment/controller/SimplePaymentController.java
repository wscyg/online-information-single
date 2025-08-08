package com.platform.payment.controller;

import com.platform.common.result.Result;
import com.platform.payment.dto.PaymentRequest;
// import com.platform.payment.service.AlipayService;
import com.platform.payment.service.AlipayCallbackService;
import com.platform.payment.vo.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.math.BigDecimal;

@Tag(name = "支付管理", description = "支付相关接口")
@RestController
@RequestMapping("/payment")
@Validated
public class SimplePaymentController {
    
    private static final Logger log = LoggerFactory.getLogger(SimplePaymentController.class);
    
    // @Autowired
    // private AlipayService alipayService;
    
    @Autowired
    private AlipayCallbackService alipayCallbackService;
    
    @Operation(summary = "创建APP支付订单")
    @PostMapping("/alipay/app")
    public Result<PaymentResponse> createAppPayment(@Valid @RequestBody PaymentRequest request) {
        // Temporarily disabled
        return Result.error("AlipayService temporarily disabled");
        /*
        try {
            log.info("创建APP支付订单，订单号：{}", request.getOrderNo());
            PaymentResponse response = alipayService.createAppPayment(request);
            return Result.success(response);
        } catch (Exception e) {
            log.error("创建APP支付订单失败，订单号：{}", request.getOrderNo(), e);
            return Result.error("创建APP支付订单失败：" + e.getMessage());
        }
        */
    }
    
    @Operation(summary = "创建PC网站支付订单") 
    @PostMapping("/alipay/page")
    public Result<PaymentResponse> createPagePayment(@Valid @RequestBody PaymentRequest request) {
        // Temporarily disabled
        return Result.error("AlipayService temporarily disabled");
        /*
        try {
            log.info("创建PC网站支付订单，订单号：{}", request.getOrderNo());
            PaymentResponse response = alipayService.createPagePayment(request);
            return Result.success(response);
        } catch (Exception e) {
            log.error("创建PC网站支付订单失败，订单号：{}", request.getOrderNo(), e);
            return Result.error("创建PC网站支付订单失败：" + e.getMessage());
        }
        */
    }
    
    @Operation(summary = "创建扫码支付订单")
    @PostMapping("/alipay/qrcode")
    public Result<PaymentResponse> createQrCodePayment(@Valid @RequestBody PaymentRequest request) {
        // Temporarily disabled
        return Result.error("AlipayService temporarily disabled");
        /*
        try {
            log.info("创建扫码支付订单，订单号：{}", request.getOrderNo());
            PaymentResponse response = alipayService.createQrCodePayment(request);
            return Result.success(response);
        } catch (Exception e) {
            log.error("创建扫码支付订单失败，订单号：{}", request.getOrderNo(), e);
            return Result.error("创建扫码支付订单失败：" + e.getMessage());
        }
        */
    }
    
    @Operation(summary = "查询支付状态")
    @GetMapping("/alipay/query/{orderNo}")
    public Result<String> queryPaymentStatus(@PathVariable String orderNo) {
        // Temporarily disabled
        return Result.error("AlipayService temporarily disabled");
        /*
        try {
            log.info("查询支付状态，订单号：{}", orderNo);
            String status = alipayService.queryPaymentStatus(orderNo);
            return Result.success(status);
        } catch (Exception e) {
            log.error("查询支付状态失败，订单号：{}", orderNo, e);
            return Result.error("查询支付状态失败：" + e.getMessage());
        }
        */
    }
    
    @Operation(summary = "支付宝回调通知")
    @PostMapping("/alipay/notify")
    public String alipayNotify(HttpServletRequest request) {
        try {
            log.info("收到支付宝回调通知");
            boolean verified = alipayCallbackService.verifyNotification(request);
            if (verified) {
                log.info("支付宝回调验证成功");
                return "success";
            } else {
                log.error("支付宝回调验证失败");
                return "failure";
            }
        } catch (Exception e) {
            log.error("处理支付宝回调通知异常", e);
            return "failure";
        }
    }

    @Operation(summary = "支付宝同步回调")
    @GetMapping("/alipay/return")
    public RedirectView alipayReturn(HttpServletRequest request) {
        boolean verified = alipayCallbackService.verifyNotification(request);
        
        if (verified) {
            return new RedirectView("/payment-success.html");
        } else {
            return new RedirectView("/payment-error.html");
        }
    }
    
    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("支付服务运行正常");
    }
}