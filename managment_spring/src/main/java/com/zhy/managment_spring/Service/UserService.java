package com.zhy.managment_spring.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhy.managment_spring.Entity.User;
import com.zhy.managment_spring.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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



}
