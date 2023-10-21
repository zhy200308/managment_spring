package com.zhy.managment_spring.Entity;

import lombok.Data;

/**
 * @author zhy
 * @date 2023/10/11 18:51
 */
@Data
public class UserLoginDto {
    private String nickname;
    private String avatarUrl;
    private String email;
    private String username;
    private String password;
    private String token;

}
