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
        User one=getUserRegistreInfo(userRegisterDto);
        if (one ==null){
            one =new User();
            BeanUtil.copyProperties(userRegisterDto,one,true);
            saveUser(one);
        }else {
            throw new ServiceException(Constants.CODE_600,"用户已经存在");
        }
        return null;
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
    private User getUserRegistreInfo(UserRegisterDto userRegisterDto){
        QueryWrapper<User> RegisterWrapper =new QueryWrapper<>();
        RegisterWrapper.eq("username",userRegisterDto.getUsername());
        RegisterWrapper.eq("password",userRegisterDto.getPassword());

        User one;
        try{
            one = getOne(RegisterWrapper);

        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"注册失败，用户已经存在");
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

/**
 * Mybatis写法
 * */

//    @Autowired
//    private UserMapper userMapper;
//
//    public int save(User user) {
//        if (user.getId() == null || user.getId().isEmpty()) {
//            user.setId(UUID.randomUUID().toString().replace("-","").toLowerCase());  // 生成新的 UUID
//            return userMapper.insert(user);
//        } else {
//            return userMapper.updateById(user);
//        }
//    }
//
//    public List<User> findAll(){
//        return userMapper.findAll();
//    }
//
//
//    public Integer deleteById(Integer id) {
//        return userMapper.deleteById(id);
//    }
//
//    public User findLimitById(Integer id) {
//        return userMapper.findLimitById(id);
//    }
//
//
//    public List<User> selectPage(Integer pageNum, Integer pageSize,String username,String email,String phone) {
//    return userMapper.selectPage(pageNum,pageSize,username,email,phone);
//    }
//
//
//    public Integer selectPageTotal(String username , String email , String phone) {
//        try {
//            Integer  total = userMapper.selectPageTotal(username, email, phone);
//            return total;
//        } catch (Exception e) {
//            // 捕获异常并输出错误信息
//            e.printStackTrace();
//            // 可以根据需要进行异常处理，比如记录日志或者返回特定的错误码
//            return -1; // 示例中返回-1表示异常
//        }
//    }
