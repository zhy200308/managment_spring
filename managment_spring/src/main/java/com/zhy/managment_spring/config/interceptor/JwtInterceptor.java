package com.zhy.managment_spring.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zhy.managment_spring.Entity.User;
import com.zhy.managment_spring.Service.UserService;
import com.zhy.managment_spring.common.Constants;
import com.zhy.managment_spring.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhy
 * @date 2023/10/16 13:32
 */

public class JwtInterceptor implements HandlerInterceptor {


    @Autowired
    private UserService userService;

  @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
            // 执行认证
            if (StrUtil.isBlank(token)) {
                throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
            }
            // 获取 token 中的 user id
            String userId;
            try {
                userId = JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException j) {
                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录") ;
            }
            User user = userService.getById(userId);
            if (user == null) {
                throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
            }
            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            try {
                jwtVerifier.verify(token);
            } catch (JWTVerificationException e) {
                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
            return true;
        }
    }


