package com.tianhengyun.common.tang4jbase.exception;

import com.tianhengyun.common.tang4jbase.support.HttpCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

public abstract class AbstractException extends RuntimeException {

    public AbstractException() {
    }

    public AbstractException(Throwable ex) {
        super(ex);
    }

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Throwable ex) {
        super(message, ex);
    }

    public void handler(ModelMap modelMap) {
        modelMap.put("code", this.getCode().value());
        if (StringUtils.isNotBlank(this.getMessage())) {
            modelMap.put("msg", this.getMessage());
        } else {
            modelMap.put("msg", this.getCode().msg());
        }

        modelMap.put("timestamp", System.currentTimeMillis());
    }

    protected abstract HttpCode getCode();

}
