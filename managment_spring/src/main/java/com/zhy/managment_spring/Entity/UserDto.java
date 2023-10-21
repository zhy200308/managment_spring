package com.zhy.managment_spring.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;
/**
 * @author zhy
 * @date 2023/10/10 17:23
 */
@Data
@ToString
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String role;
    private String email;
    private String nickname;
    private String phone;
    private String address;
    @TableField(value = "avatarUrl")
    private String avatarUrl;

}
