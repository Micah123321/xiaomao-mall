package com.xiaomao6.common.constant;

/**
 * @ClassName PurchaseStatusConstant
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/27 23:55
 * @Version 1.0
 **/
public class WareConstant {
    public enum PurchaseDetailStatusConstant{
        ERROR(4,"采购失败"),
        FINISH(3,"已完成"),
        DOING(2,"正在采购"),
        ASSIGN(1,"已分配"),
        NEW(0,"新建"),
        ;
        private final Integer code;
        private final String name;

        PurchaseDetailStatusConstant(Integer code, String name) {
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
    public enum PurchaseStatusConstant{
        ERROR(4,"有异常"),
        FINISH(3,"已完成"),
        RECEIVE(2,"已领取"),
        ASSIGN(1,"已分配"),
        NEW(0,"新建"),
        ;
        private final Integer code;
        private final String name;

        PurchaseStatusConstant(Integer code, String name) {
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
