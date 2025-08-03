package com.platform.payment.controller;

import com.platform.common.result.Result;
import com.platform.payment.dto.PaymentDTO;
import com.platform.payment.service.SimplePaymentService;
import com.platform.payment.service.AlipayCallbackService;
import com.platform.payment.vo.PaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "支付管理", description = "支付相关接口")
@RestController
@RequestMapping("/payment")
public class SimplePaymentController {
    
    @Autowired
    private SimplePaymentService paymentService;
    
    @Autowired
    private AlipayCallbackService alipayCallbackService;
    
    @Operation(summary = "创建支付订单")
    @PostMapping("/create")
    public Result<PaymentVO> createPayment(@RequestBody PaymentDTO payment) {
        PaymentVO result = paymentService.createPayment(payment);
        return Result.success(result);
    }
    
    @Operation(summary = "查询支付状态")
    @GetMapping("/query/{orderNo}")
    public Result<String> queryPayment(@PathVariable String orderNo) {
        String status = paymentService.queryPayment(orderNo);
        return Result.success(status);
    }
    
    @Operation(summary = "支付宝回调验证")
    @PostMapping("/alipay/notify")
    public String alipayNotify(HttpServletRequest request) {
        boolean verified = alipayCallbackService.verifyNotification(request);
        if (verified) {
            return "success";
        } else {
            return "failure";
        }
    }
    
    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("Payment service is running");
    }
}