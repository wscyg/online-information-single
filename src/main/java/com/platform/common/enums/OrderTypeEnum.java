package com.platform.common.enums;

public enum OrderTypeEnum {
    COLUMN("column", "专栏订阅"),
    VIP("vip", "VIP会员"),
    CONTENT("content", "单篇内容"),
    RECHARGE("recharge", "余额充值");

    private final String code;
    private final String desc;

    OrderTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderTypeEnum getByCode(String code) {
        for (OrderTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}