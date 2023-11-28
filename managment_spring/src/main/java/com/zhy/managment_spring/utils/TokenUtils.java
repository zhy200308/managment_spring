package com.zhy.managment_spring.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zhy.managment_spring.Entity.User;
import com.zhy.managment_spring.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zhy
 * @date 2023/10/15 21:45
 */
@Component
public class TokenUtils {

    private static UserService staticUserService;
   @Resource
    private UserService userService;

   @PostConstruct
   public void setUserService(){
       staticUserService=userService;
   }

   /*
   * 获取当前登录的用户信息
   * */

    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                System.out.println("用户id：" + userId);
                // 获取用户信息并设置到当前用户
                User currentUser = staticUserService.getById(String.valueOf(userId));
                return currentUser;
            }
        } catch (Exception e) {
            // 打印异常信息，以便调试
            e.printStackTrace();
        }
        return null;
    }



    //生成token

    public static String getToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 userId 保存到 token 里面
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥

    }


}
