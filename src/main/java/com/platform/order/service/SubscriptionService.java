package com.platform.order.service;

import com.platform.order.entity.Order;

public interface SubscriptionService {

    void createColumnSubscription(Order order);

    void createVipSubscription(Order order);

    void createContentAccess(Order order);

    boolean hasAccess(Long userId, Long columnId);

    boolean isVip(Long userId);
}