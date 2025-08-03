package com.platform.payment.service;

import com.platform.payment.dto.PaymentDTO;
import com.platform.payment.dto.RefundDTO;
import com.platform.payment.vo.PaymentVO;

public interface PaymentService {

    PaymentVO createPayment(PaymentDTO payment);

    String queryPaymentStatus(String orderNo);

    void processRefund(RefundDTO refund);
}