package com.zhy.managment_spring.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhy.managment_spring.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zhy
 * @date 2023/10/3 21:22
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{
//    @Select("SELECT * from user;")
//    List<User> findAll();
//
//    @Select("INSERT INTO user(id,username,password,role,nickname,phone,email,address) VALUES(#{id},#{username},#{password},#{role},#{nickname},#{phone},#{email},#{address})")
//    Integer insert(User user);
//
//
//    Integer updateById(User user);
//
//    @Delete("delete  from user where id=#{id}")
//    Integer deleteById (@Param("id") Integer id);
//
//    @Select("select * from user where id=#{id}")
//    User findLimitById(Integer id);
//
//    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%', #{username}, '%') " +
//            "AND COALESCE(phone, '') LIKE CONCAT('%', #{phone}, '%') " +
//            "AND COALESCE(email, '') LIKE CONCAT('%', #{email}, '%') " +
//            "LIMIT #{pageNum}, #{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize,String username,String email,String phone);
//
//    @Select("SELECT COUNT(*) FROM user WHERE username LIKE CONCAT('%', #{username}, '%') " +
//            "AND COALESCE(phone, '') LIKE CONCAT('%', #{phone}, '%') " +
//            "AND COALESCE(email, '') LIKE CONCAT('%', #{email}, '%')")
//    Integer selectPageTotal(String username, String email, String phone);

/**
 * Mybatis-plus写法
 * */
}
