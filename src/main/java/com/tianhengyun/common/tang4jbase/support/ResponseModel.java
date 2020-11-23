package com.tianhengyun.common.tang4jbase.support;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ResponseModel {

    private Integer code;
    private String message;
    private Object data;
    /**
     * 默认响应成功,携带其他数据
     * @param data 携带的数据
     */
    public ResponseModel(Object data) {
        this.code = HttpCode.OK.getCode();
        this.message = HttpCode.OK.getMsg();
        this.data = data;
    }


    /**
     * 自定义响应代码信息，携带其他数据
     * @param code 响应编号
     * @param message 响应信息
     * @param data 携带的数据
     */
    public ResponseModel(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 从HttpCode选取对应的响应信息，携带其他数据
     * @param httpCode HttpCode
     * @param data 携带的数据
     */
    public ResponseModel(HttpCode httpCode, Object data) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
        this.data = data;
    }

    /**
     * 自定义响应代码信息,不携带其他数据
     * @param code 响应编号
     * @param message 响应信息
     */
    public ResponseModel(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 从HttpCode选取对应的响应信息,不携带其他数据
     * @param httpCode httpCode
     */
    public ResponseModel(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public ResponseModel setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseModel setData(Object data) {
        this.data = data;
        return this;
    }

    public static class Resources {

        public static final ResourceBundle THIRDPARTY = ResourceBundle.getBundle("config/thirdParty");
        private static final Map<String, ResourceBundle> MESSAGES = new HashMap();

        public Resources() {
        }

        public static String getMessage(String key, Object... params) {
            Locale locale = LocaleContextHolder.getLocale();
            ResourceBundle message = (ResourceBundle) MESSAGES.get(locale.getLanguage());
            if (message == null) {
                Map var4 = MESSAGES;
                synchronized (MESSAGES) {
                    message = (ResourceBundle) MESSAGES.get(locale.getLanguage());
                    if (message == null) {
                        message = ResourceBundle.getBundle("i18n/messages", locale);
                        MESSAGES.put(locale.getLanguage(), message);
                    }
                }
            }

            return params != null && params.length > 0 ? String.format(message.getString(key), params) : message.getString(key);
        }

        public static void flushMessage() {
            MESSAGES.clear();
        }

    }
}
