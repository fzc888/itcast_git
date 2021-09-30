package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*@Controller
@ResponseBody//响应jsons数据到页面*/
@RestController //等价于上面两个注解
@RequestMapping(value = "/user")
public class UserController {
    // @Autowired
    @Reference //订阅
    UserService userService;

    //主键查询
    @RequestMapping(value = "/findById")
    public User finById(Integer id){
        User user = userService.findById(id);
        return user;//以json的方式传递
    }
}
