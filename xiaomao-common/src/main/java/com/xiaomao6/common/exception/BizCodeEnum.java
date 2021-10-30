package com.xiaomao6.common.exception;

/**
 * 定义系统状态码的枚举
 */
public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000,"系统未知错误"),
    VALID_EXCEPTION(10001,"参数格式校验失败"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架错误"),
    ;
    private final Integer code;
    private final String msg;

    BizCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
