package com.xiaomao6.common.constant;

/**
 * @ClassName AttrTypeEnum
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/21 21:44
 * @Version 1.0
 **/
public enum AttrTypeEnum {
    ATTR_TYPE_BASE(1,"基本类型"),
    ATTR_TYPE_SALE(0,"销售类型"),
            ;
    private final Integer code;
    private final String name;

    AttrTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
