package com.platform.common.constant;

public class MQConstants {

    // 交换机
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_DELAY_EXCHANGE = "order.delay.exchange";
    public static final String NOTIFY_EXCHANGE = "notify.exchange";

    // 队列
    public static final String ORDER_PAY_QUEUE = "order.pay.queue";
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";
    public static final String EMAIL_QUEUE = "email.queue";
    public static final String SMS_QUEUE = "sms.queue";

    // 路由键
    public static final String ORDER_PAY_KEY = "order.pay";
    public static final String ORDER_DELAY_KEY = "order.delay";
    public static final String EMAIL_KEY = "email";
    public static final String SMS_KEY = "sms";
}