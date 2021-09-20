package com.xiaomao6.xiaomaoproduct.exception;

import com.xiaomao6.common.exception.BizCodeEnum;
import com.xiaomao6.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName XiaomaoExceptionControllerAdvice
 * @Author Micah
 * @Date 2021/9/18 20:22
 * @Version 1.0
 **/
@Slf4j
@RestControllerAdvice(basePackages = "com.xiaomao6.xiaomaoproduct.controller")
public class XiaomaoExceptionControllerAdvice {
    /**
     * 统一处理校验错误异常
     * @param exception 拦截到的校验错误异常
     * @return 错误提示数据
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(e -> {
            stringStringHashMap.put(e.getField(), e.getDefaultMessage());
            log.debug(e.getDefaultMessage());
            System.out.println(e.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", stringStringHashMap);
    }

    /**
     * 未知异常处理
     * @param exception 异常
     * @return 未知异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception exception) {
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg()).put("data", exception.getMessage());
    }
}
