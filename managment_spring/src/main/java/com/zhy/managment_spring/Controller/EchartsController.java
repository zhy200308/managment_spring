package com.zhy.managment_spring.Controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.zhy.managment_spring.Entity.User;
import com.zhy.managment_spring.Service.UserService;
import com.zhy.managment_spring.common.Result;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author zhy
 * @date 2023/10/23 19:14
 */
@RestController
@RequestMapping("echarts")
public class EchartsController {

    @Autowired
    public UserService userService;

    @GetMapping("/members")
    public Result members(){

        List<User> list=userService.list();
        int q1=0;//第一季度
        int q2=0;//第二季度
        int q3=0;//第三季度
        int q4=0;//第四季度
        for (User user:list){
            Date creatTime=user.getCreatTime();
            Quarter quarter =DateUtil.quarterEnum(creatTime);
        switch (quarter){
            case Q1:q1+=1;break;
            case Q2:q2+=1;break;
            case Q3:q3+=1;break;
            case Q4:q4+=1;break;
            default:break;
        }
        }
        return  Result.success(CollUtil.newArrayList(q1,q2,q3,q4));

    }
}
