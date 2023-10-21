package com.zhy.managment_spring.exception;

import com.zhy.managment_spring.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhy
 * @date 2023/10/12 19:48
 */
@ControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
    return Result.error(se.code,se.getMessage());
}
}
