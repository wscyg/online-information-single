package com.platform.common.result;

public enum ResultCode {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    PARAM_ERROR(400, "参数错误"),
    TOKEN_EXPIRED(4001, "token已过期"),
    TOKEN_INVALID(4002, "token无效"),
    USER_NOT_FOUND(4003, "用户不存在"),
    PASSWORD_ERROR(4004, "密码错误"),
    USER_DISABLED(4005, "用户已禁用"),
    DUPLICATE_KEY(4006, "数据重复");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}