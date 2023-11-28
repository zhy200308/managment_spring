package com.zhy.managment_spring.Entity;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

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
    private Date creatTime;
    @TableField(value = "avatarUrl")
    private String avatarUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    public Date getCreatTime() {
        return creatTime;
    }
}
