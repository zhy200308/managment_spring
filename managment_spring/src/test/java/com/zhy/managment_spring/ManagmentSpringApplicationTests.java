package com.zhy.managment_spring;

import static org.junit.jupiter.api.Assertions.assertEquals; // 添加这行导入

import com.zhy.managment_spring.Controller.UserController;
import com.zhy.managment_spring.Entity.User;
import com.zhy.managment_spring.Mapper.UserMapper;
import com.zhy.managment_spring.Service.UserService;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ManagmentSpringApplicationTests {
@Autowired
private UserController userController;
@Autowired
private UserService userService;
@Autowired
private UserMapper userMapper;

    @Test
    void contextLoads() {
    }
    @Test
    void testRegistration() {
        // 创建一个新用户
        User newUser = new User();
        newUser.setUsername("testuser");
        newUser.setPassword("testpassword");
        newUser.setEmail("test@example.com");
    }



}
