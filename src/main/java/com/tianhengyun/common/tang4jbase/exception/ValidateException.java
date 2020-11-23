package com.tianhengyun.common.tang4jbase.exception;

import com.tianhengyun.common.tang4jbase.support.HttpCode;

/**
 * 检验异常
 */
public class ValidateException extends AbstractException {

    public ValidateException() {
    }

    public ValidateException(Throwable ex) {
        super(ex);
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable ex) {
        super(message, ex);
    }

    protected HttpCode getCode() {
        return HttpCode.PRECONDITION_FAILED;
    }
}
