package com.tianhengyun.common.tang4jbase.support;

/**
 * 返回结果类型枚举
 */

public enum HttpCode {

    OK(200, "成功" ),
    ERROR(201, "异常" ),
    MULTI_STATUS(207),
    BAD_REQUEST(400, "参数异常" ),
    UNAUTHORIZED(401, "未登录或已过期" ),
    LOGIN_FAIL(402, "登录失败" ),
    FORBIDDEN(403, "禁止访问" ),
    NOT_FOUND(404, "数据不存在" ),
    METHOD_NOT_ALLOWED(405, "请求不被允许" ),
    NOT_ACCEPTABLE(406, "非法请求" ),
    PERMISSION_DENIED(407, "权限不足，无法访问该资源" ),
    REQUEST_TIMEOUT(408, "请求超时" ),
    CONFLICT(409, "请求冲突" ),
    GONE(410, "请求不可用" ),
    LENGTH_REQUIRED(411, "长度异常" ),
    PRECONDITION_FAILED(412, "预处理失败" ),
    ENTITY_TOO_LARGE(413, "请求实体太大" ),
    UNSUPPORTED_MEDIA_TYPE(415, "格式不支持" ),
    AUTHENTICATION_FAILED(416, "接口，因为认证失败，无法访问系统资源" ),
    LOGIN_FAILED_ACCOUNT(417, "用户账号错误" ),
    LOGIN_FAILED_PASSWORD(419, "用户密码错误" ),
    ACCOUNT_FORBIDDEN(418, "账户被禁用，请联系管理员" ),
    TOO_MANY_CONNECTIONS(421, "连接数溢出" ),
    LOCKED(423, "资源被锁定" ),
    UNAVAILABLE_LEGAL(451, "法律原因不可用" ),
    INTERNAL_SERVER_ERROR(500, "系统异常" ),
    NOT_IMPLEMENTED(501, "不支持当前请求" ),
    SERVICE_UNAVAILABLE(503, "服务器维护或者过载" ),
    NOT_EXTENDED(510, "策略不足" );

    private Integer code;
    private String msg;

    private HttpCode(Integer code) {
        this.code = code;
    }

    private HttpCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    public Integer value() {
        return this.code;
    }

    public String msg() {
        return ResponseModel.Resources.getMessage("HTTP_CODE_" + this.code, this.msg);
    }

    public String toString() {
        return this.code.toString();
    }
}
