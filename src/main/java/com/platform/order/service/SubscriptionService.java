package com.platform.order.service;

import com.platform.order.entity.Order;
import com.platform.order.entity.ColumnSubscription;

public interface SubscriptionService {

    void createColumnSubscription(Order order);

    void createVipSubscription(Order order);

    void createContentAccess(Order order);

    boolean hasAccess(Long userId, Long columnId);

    boolean isVip(Long userId);
    
    java.util.List<com.platform.order.entity.ColumnSubscription> getUserSubscriptions(Long userId);
    
    ColumnSubscription findByOrderNo(String orderNo);
    
    void updateSubscription(ColumnSubscription subscription);
}