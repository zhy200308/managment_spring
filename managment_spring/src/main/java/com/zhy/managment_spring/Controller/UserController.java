package com.zhy.managment_spring.Controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhy.managment_spring.Entity.*;
import com.zhy.managment_spring.Service.UserService;
import com.zhy.managment_spring.common.Constants;
import com.zhy.managment_spring.common.Result;
import com.zhy.managment_spring.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    public UserUpdateDto saveUser(@RequestBody User user){
        userService.saveUser(user);
        return userService.userToUserDTO(user);
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

        //获取当前用户信息
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null) {
            System.out.println("————————当前用户信息——————" + currentUser.getNickname());
        } else {
            System.out.println("当前用户为空");
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

    /*
     *导出
     * */

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{

        // 查询所有数据
        // 查询所有数据
        List<User> userList = userService.list();

        // 创建一个新的UserDto列表
        List<UserDto> list = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            list.add(userDto);
        }
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        // 通过工具类创建writer写到磁盘路径
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 自定义别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("role", "角色");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");

        // 一次性写出list内的对象到excel，使用默认的样式，强制输出标题
        writer.write(list, true);

        // 将Excel数据写入响应输出流
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();

    }
    /*
     * 导入
     * */
    @PostMapping("/import")
    public void imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 为每个字段添加别名
        reader.addHeaderAlias("用户名", "username");
        reader.addHeaderAlias("密码", "password");
        reader.addHeaderAlias("昵称", "nickname");
        reader.addHeaderAlias("角色", "role");
        reader.addHeaderAlias("电话", "phone");
        reader.addHeaderAlias("地址", "address");
        reader.addHeaderAlias("邮箱", "email");
        reader.addHeaderAlias("创建时间", "createTime");
        reader.addHeaderAlias("头像", "avatarUrl");
        List<User> list = reader.readAll(User.class);
        for (User user : list) {
            userService.saveOrUpdateUser(user);
        }
    }
    /*
     * 登录
     * */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto){
        String username=userLoginDto.getUsername();
        String password=userLoginDto.getPassword();
        if (StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserLoginDto userLoginDto1=userService.login(userLoginDto);
        return Result.success(userLoginDto1);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto){
        String username=userRegisterDto.getUsername();
        String password=userRegisterDto.getPassword();

        if (StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userRegisterDto));
    }
    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);

        return Result.success(userService.getOne(queryWrapper));
    }


}
