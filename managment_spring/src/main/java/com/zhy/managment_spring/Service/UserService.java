package com.zhy.managment_spring.Service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhy.managment_spring.Entity.User;
import com.zhy.managment_spring.Entity.UserLoginDto;
import com.zhy.managment_spring.Entity.UserRegisterDto;
import com.zhy.managment_spring.Entity.UserUpdateDto;
import com.zhy.managment_spring.Mapper.UserMapper;
import com.zhy.managment_spring.common.Constants;
import com.zhy.managment_spring.exception.ServiceException;
import com.zhy.managment_spring.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author zhy
 * @date 2023/10/3 23:14
 */
@Service
public class UserService extends ServiceImpl<UserMapper ,User> {

    /**
     * Mybatis-plus写法
     * */
    public boolean saveUser(User user){

        if (user.getId() == null || user.getId().isEmpty()) {
            // 如果ID为null或为空，则生成新的UUID
            user.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            return save(user);
        }else {
            return updateById(user);
        }
    }

    public void saveOrUpdateUser(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString().replace("-","").toLowerCase());
        }
        this.saveOrUpdate(user);
    }

    public UserLoginDto login(UserLoginDto userLoginDto) {
        User one=getUserLoginInfo(userLoginDto);
        if (one!=null){
            BeanUtil.copyProperties(one,userLoginDto,true);
            //设置token
            String token=TokenUtils.getToken(one.getId().toString(),one.getPassword());
            userLoginDto.setToken(token);
            return userLoginDto;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }


    }

    public User register(UserRegisterDto userRegisterDto) {
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();

        // 检查用户名是否已存在
        if (isUsernameExists(username)) {
            throw new ServiceException(Constants.CODE_600, "用户已经存在");
        }

        // 如果用户名不存在，执行注册逻辑
        User newUser = new User();
        BeanUtil.copyProperties(userRegisterDto, newUser, true);
        saveUser(newUser);

        return newUser;
    }
    private boolean isUsernameExists(String username) {
        QueryWrapper<User> usernameCheckWrapper = new QueryWrapper<>();
        usernameCheckWrapper.eq("username", username);
        return count(usernameCheckWrapper) > 0;
    }


    private User getUserLoginInfo(UserLoginDto userLoginDto){
        QueryWrapper<User> queryWrapperL=new QueryWrapper<>();
        queryWrapperL.eq("username",userLoginDto.getUsername());
        queryWrapperL.eq("password",userLoginDto.getPassword());

        User one;
        try{
            one = getOne(queryWrapperL);

        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }

    //返回前端的数据
    public UserUpdateDto userToUserDTO(User user) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setUsername(user.getUsername());
        userUpdateDto.setPassword(user.getPassword());
        userUpdateDto.setRole(user.getRole());
        userUpdateDto.setEmail(user.getEmail());
        userUpdateDto.setNickname(user.getNickname());
        userUpdateDto.setPhone(user.getPhone());
        userUpdateDto.setAddress(user.getAddress());
        userUpdateDto.setAvatarUrl(user.getAvatarUrl());
        return userUpdateDto;
    }


}
