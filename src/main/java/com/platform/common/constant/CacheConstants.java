package com.platform.common.constant;

public class CacheConstants {

    // 缓存前缀
    public static final String CACHE_PREFIX = "platform:";

    // 用户相关
    public static final String USER_TOKEN = CACHE_PREFIX + "user:token:";
    public static final String USER_INFO = CACHE_PREFIX + "user:info:";
    public static final String SMS_CODE = CACHE_PREFIX + "sms:code:";
    public static final String EMAIL_CODE = CACHE_PREFIX + "email:code:";

    // 内容相关
    public static final String COLUMN_DETAIL = CACHE_PREFIX + "column:detail:";
    public static final String CONTENT_DETAIL = CACHE_PREFIX + "content:detail:";
    public static final String HOT_COLUMNS = CACHE_PREFIX + "hot:columns";

    // 订单相关
    public static final String ORDER_PAY_LOCK = CACHE_PREFIX + "order:pay:lock:";

    // 缓存时间（秒）
    public static final long TOKEN_EXPIRE = 7 * 24 * 60 * 60; // 7天
    public static final long SMS_CODE_EXPIRE = 5 * 60; // 5分钟
    public static final long CONTENT_EXPIRE = 60 * 60; // 1小时
}