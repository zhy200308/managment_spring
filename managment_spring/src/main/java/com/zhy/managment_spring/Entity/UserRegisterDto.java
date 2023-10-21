package com.zhy.managment_spring.Entity;

import lombok.Data;

/**
 * @author zhy
 * @date 2023/10/13 14:57
 */
@Data
public class UserRegisterDto {
    private String nickname;
    private String avatarUrl;
    private String email;
    private String username;
    private String password;

}
