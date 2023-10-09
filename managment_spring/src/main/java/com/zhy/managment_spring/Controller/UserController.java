package com.zhy.managment_spring.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhy.managment_spring.Entity.User;
import com.zhy.managment_spring.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhy
 * @date 2023/10/3 21:37
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Mybatis-plus写法
     * */
    @PostMapping
        public boolean saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize ,
                                        @RequestParam(defaultValue = "") String username,
                                        @RequestParam(defaultValue = "") String email,
                                        @RequestParam(defaultValue = "") String phone){

        IPage<User> page=new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (!"".equals(username)){
            queryWrapper.like("username",username);

        }
        if (!"".equals(email)){
            queryWrapper.like("email",email);
        }
        if (!"".equals(phone)){
            queryWrapper.like("phone",phone);
        }
        queryWrapper.orderByDesc("creatTime");
        return userService.page(page,queryWrapper);
    }
    @PostMapping("/updata/{id}")
    public User getUserById(@PathVariable String id){
        return userService.getById(id);
    }

    @DeleteMapping("/delId={id}")
    public boolean deleteUserById(@PathVariable(value = "id") String id) {
       return userService.removeById(id);

    }

    @PostMapping("/delete/delBatch")
    public boolean deleteBatchByIds(@RequestBody List<String> ids) {
        // 在此处处理批量删除逻辑
        return userService.removeByIds(ids);
    }
    /**
     * Mybatis写法
     * */


//    @PostMapping
//    public Integer save(@RequestBody User user){
//        return userService.save(user);
//    }
//
//
//    @GetMapping
//    public List<User> findUserAll(){
//
//
//        return userService.findAll();
//    }
//    @DeleteMapping("/{id}")
//    public Integer deleteById(@PathVariable Integer id){
//        return  userService.deleteById(id);
//    }
//    @GetMapping("/findById/{id}")
//    public User findLimitById(@PathVariable Integer id){
//        return userService.findLimitById(id);
//    }
////    分页查询
////    接口路径：/user/page
////    @RequestParam接受 ？pagNum=1&pageSize=1
////    limit第一个参数=（pageNum-1）*pageSize
//    @GetMapping("/page")
//    public Map<String ,Object> findPage(@RequestParam Integer pageNum,
//                                        @RequestParam Integer pageSize ,
//                                        @RequestParam String username,
//                                        @RequestParam String email,
//                                        @RequestParam String phone){
//        pageNum=(pageNum-1)*pageSize;
//        Integer  Total=userService.selectPageTotal(username,email,phone);
//        List<User> data= (List<User>) userService.selectPage(pageNum,pageSize,username,email,phone);
//    Map<String ,Object> res=new HashMap<>();
//    res.put("data",data);
//    res.put("Total",Total);
//    return res;
//    }

}


