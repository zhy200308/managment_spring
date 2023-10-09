package com.zhy.managment_spring.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.temporal.ValueRange;

/**
 * @author zhy
 * @date 2023/10/3 21:17
 */
@Data
@TableName(value="user")
public class User {

    @TableId(type = IdType.INPUT)
    private String  id;
    private String username;
    private String password;
    private String role;
    private String email;
    private String nickname;
    private String phone;
    private String address;
    @TableField(value = "creatTime")
    private String creatTime;


}
