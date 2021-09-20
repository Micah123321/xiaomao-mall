package com.xiaomao6.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName ListValueConstraintValidator
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/18 22:52
 * @Version 1.0
 **/
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {


    Set<Integer> set= new HashSet<>();


    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        for (int val : vals) {
            set.add(val);
        }
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(integer);
    }
}
