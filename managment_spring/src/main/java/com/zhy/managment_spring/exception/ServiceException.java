package com.zhy.managment_spring.exception;

/**
 * @author zhy
 * @date 2023/10/12 19:52
 */
public class ServiceException extends RuntimeException {

    String code;
    public ServiceException(String code, String msg){
        super(msg);
        this.code=code;
    }
}
