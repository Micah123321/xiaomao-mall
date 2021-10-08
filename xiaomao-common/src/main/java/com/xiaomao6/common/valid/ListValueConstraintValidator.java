package com.xiaomao6.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * ConstraintValidator<注解名字,参数处理类型>
 * @ClassName ListValueConstraintValidator
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/18 22:52
 * @Version 1.0
 **/
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {


    //创建一个set用于存储被邀请输入的值集合
    Set<Integer> set= new HashSet<>();


    /**
     * 初始化注解方法
     * @param constraintAnnotation 注解对象,可以通过vals()方法获取值集合
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        for (int val : vals) {
            set.add(val);
        }
    }

    /**
     * 每次判断进入的方法
     * @param integer 前台传入过来的参数
     * @param constraintValidatorContext   主要的方法是使用传递的ConstraintValidatorContext对象可以添加额外的错误消息，或者完全禁用默认的错误信息而使用完全自定义的错误信息。
     * @return 判断是否包含
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(integer);
    }
}
