package com.xiaomao6.common.constant;

/**
 * @ClassName ProductConstant
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/18 18:10
 * @Version 1.0
 **/
public class ProductConstant {
     public enum status{
        DOWN(2,"下降"),
        UP(1,"上架"),
        NEW(0,"新建"),
        ;
        private final Integer code;
        private final String name;

        status(Integer code, String name) {
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
}
