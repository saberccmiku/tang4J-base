package com.tianhengyun.common.tang4jbase.support;

/**
 * 请求响应工厂类
 * @author fjy
 */
public class ResponseModelFactory {

    /**
     * 默认响应成功,不携带其他数据
     */
    public static ResponseModel OK() {
        return new ResponseModel(HttpCode.OK);
    }

    /**
     * 默认响应成功,不携带其他数据
     */
    public static ResponseModel OK(String msg) {
        return new ResponseModel(HttpCode.OK.getCode(),msg);
    }

    /**
     * 默认响应成功,携带其他数据
     *
     * @param data 携带的数据
     */

    public static ResponseModel OKWithData(Object data) {
        return new ResponseModel(HttpCode.OK, data);
    }

    /**
     * 从HttpCode选取对应的响应信息,不携带其他数据
     * @param httpCode httpCode
     */

    public static ResponseModel createByHttpCode(HttpCode httpCode) {
        return new ResponseModel(httpCode);
    }

    /**
     * 从HttpCode选取对应的响应信息，携带其他数据
     * @param httpCode HttpCode
     * @param data 携带的数据
     */
    public static ResponseModel createByHttpCodeWithData(HttpCode httpCode, Object data) {
        return new ResponseModel(httpCode, data);
    }

    /**
     * 自定义响应代码信息,不携带其他数据
     * @param httpCode 响应编号
     * @param message 响应信息
     */
    public static ResponseModel createByCustom(Integer httpCode, String message) {
        return new ResponseModel(httpCode, message);
    }


    /**
     * 自定义响应代码信息，携带其他数据
     * @param httpCode 响应编号
     * @param message 响应信息
     * @param data 携带的数据
     */
    public static ResponseModel createByCustomWithData(Integer httpCode, String message, Object data) {
        return new ResponseModel(httpCode, message,data);
    }


    /**
     * 异常201，不携带数据
     */
    public static ResponseModel error() {
        return new ResponseModel(HttpCode.ERROR);
    }

    /**
     * 异常201，不携带数据
     * @param msg 自定义错误信息
     */
    public static ResponseModel error(String msg) {
        return new ResponseModel(HttpCode.ERROR.getCode(),msg);
    }


    /**
     * 异常201，携带数据
     * @param msg 自定义错误信息
     */
    public static ResponseModel error(String msg, Object data) {
        return new ResponseModel(HttpCode.ERROR, msg);
    }

    /**
     * 异常，不携带其他数据
     * @param httpCode 自定义响应编号
     * @param message 自定义响应信息
     */
    public static ResponseModel error(Integer httpCode, String message) {
        return new ResponseModel(httpCode, message);
    }

    /**
     * 异常，携带其他数据
     * @param httpCode 自定义响应编号
     * @param message 自定义响应信息
     */
    public static ResponseModel errorWhitData(Integer httpCode, String message, Object data) {
        return new ResponseModel(httpCode, message,data);
    }










}
