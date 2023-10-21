package com.zhy.managment_spring.config;

import com.zhy.managment_spring.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器
 * @author zhy
 * @date 2023/10/16 14:04
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")//拦截所有请求，通过判断token是否合法决定是否需要验证
                .excludePathPatterns("/user/export","/user/register","/user/login","/user/import","/files/**");
    }
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
