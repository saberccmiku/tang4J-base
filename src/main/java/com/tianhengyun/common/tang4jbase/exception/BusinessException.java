package com.tianhengyun.common.tang4jbase.exception;

import com.tianhengyun.common.tang4jbase.support.HttpCode;

/**
 * 业务冲突异常
 */

public class BusinessException extends AbstractException {

    public BusinessException() {
    }

    public BusinessException(Throwable ex) {
        super(ex);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable ex) {
        super(message, ex);
    }

    protected HttpCode getCode() {
        return HttpCode.CONFLICT;
    }

}
