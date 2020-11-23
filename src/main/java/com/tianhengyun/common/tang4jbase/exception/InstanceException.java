package com.tianhengyun.common.tang4jbase.exception;

import com.tianhengyun.common.tang4jbase.support.HttpCode;

/**
 * 实例化异常
 */
public class InstanceException extends AbstractException {

    public InstanceException() {
    }

    public InstanceException(Throwable t) {
        super(t);
    }

    protected HttpCode getCode() {
        return HttpCode.INTERNAL_SERVER_ERROR;
    }

}
