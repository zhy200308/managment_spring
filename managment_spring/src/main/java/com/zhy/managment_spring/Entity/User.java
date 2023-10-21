package com.zhy.managment_spring.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhy
 * @date 2023/10/3 21:17
 */
@Data
@TableName(value="user")
@ToString

public class User {
    @TableId(type = IdType.INPUT)
    private String  id;
    private String password;
    private String username;
    private String role;
    private String email;
    private String nickname;
    private String phone;
    private String address;
    @TableField(value = "creatTime")
    private String creatTime;
    @TableField(value = "avatarUrl")
    private String avatarUrl;

}
