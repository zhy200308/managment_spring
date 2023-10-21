package com.zhy.managment_spring.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhy
 * @date 2023/10/12 19:28
 *
 *
 * 接口统一包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    public String code;
    public String msg;
    public Object data;
    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }
    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }
    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }
    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }
}
