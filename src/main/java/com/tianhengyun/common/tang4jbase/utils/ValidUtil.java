package com.tianhengyun.common.tang4jbase.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ValidationException;

public class ValidUtil {
    /**
     * 处理接口参数校验错误信息
     *
     * @param bindingResult 检验错误信息
     */
    public static void validData(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                sb.append(error.getDefaultMessage()).append(",");
            }
            int lastIndex = sb.toString().lastIndexOf(",");
            throw new ValidationException(sb.toString().substring(0,lastIndex));
        }
    }
}
