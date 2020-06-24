package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.result.ExceptionMsg;
import com.example.demo.result.ResponseData;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录",notes = "name和pwd都是字符串")
    public ResponseData login(
            @ApiParam(value = "账号名称") @RequestParam("username") String username,
            @ApiParam(value = "账号密码")@RequestParam("password") String password,
            HttpServletResponse response)  {
                User loginUser = userService.Login(username,password);
                if (loginUser!=null) {
                    String token=JwtUtil.getToken(loginUser);
                    response.addHeader("token", token);
                    return new ResponseData(ExceptionMsg.SUCCESS, token);
                }
                else
                    return new ResponseData(ExceptionMsg.FAILED, "error");
    }

    @PostMapping(value = "/sign")
    @ApiOperation(value = "注册",notes = "name和pwd都是字符串")
    public ResponseData ce(
            @ApiParam(value = "账号名称") @RequestParam("username") String username,
            @ApiParam(value = "账号密码")@RequestParam("password") String password,
            HttpServletResponse response)  {
        if (username!=null&&password!=null) {
            Boolean sign = userService.addUser(new User(username, password));
            if (sign == true) {
                return new ResponseData(ExceptionMsg.SUCCESS, "注册成功");
            }
            return new ResponseData(ExceptionMsg.FAILED, "注册失败，用户已存在");
        }
        else
            return new ResponseData(ExceptionMsg.FAILED, "注册失败,密码不能为空");
    }
    @PostMapping(value = "/download")
    public ResponseData download(){
        String place="src/main/resources/static/";
       return new ResponseData(ExceptionMsg.SUCCESS,"data");
    }

}
