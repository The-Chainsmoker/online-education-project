package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.login.User;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin //解决跨域
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        //if(!username.equals("admin")){
        //    throw new GuliException(20001,"用户名不正确!");
        //}
        if (!password.equals("111111")){
            throw new GuliException(20001,"密码不正确!");
        }
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info(){
        return  R.ok().data("roles","管理员").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");//avatar 头像图片
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
