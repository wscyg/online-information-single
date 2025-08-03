package com.platform.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.order.dto.CreateOrderDTO;
import com.platform.order.entity.Order;

public interface OrderService extends IService<Order> {

    Order createOrder(Long userId, CreateOrderDTO dto);

    void paySuccess(String orderNo, String tradeNo);

    void cancelOrder(String orderNo);

    void checkExpiredOrders();

    Order getByOrderNo(String orderNo);
}