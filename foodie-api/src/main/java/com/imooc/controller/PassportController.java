package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.service.UserService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "注册登陆",tags = {"用于注册登陆等相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在",notes ="用户名是否存在" ,httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username){
        //1. 判断用户名不能为空
        if(StringUtils.isBlank(username)){
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        //2. 查找注册的用户名是否为空
        boolean isExist = userService.queryUsernameIsExist(username);
        if(isExist){
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        //3. 请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户注册",notes ="用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist( @RequestBody UserBo userBo){
        String username =userBo.getUsername();
        String password =userBo.getPassword();
        String confirmPwd = userBo.getConfirmPassword();
        //1. 判断用户名和密码必须不为空
        if(StringUtils.isBlank(username)||
                StringUtils.isBlank(password)||
                StringUtils.isBlank(confirmPwd)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        //2. 判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if(isExist){
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        //3.密码长度不能少于6位
        if(password.length()<6){
            return IMOOCJSONResult.errorMsg("密码长度不能少于6");
        }
        //4. 判断两次密码是否一致
        if(!password.equals(confirmPwd)){
            return IMOOCJSONResult.errorMsg("两次密码输入不一致");
        }
        //5.实现注册
        Users users = userService.createUser(userBo);
        return IMOOCJSONResult.ok(users);
    }


    @ApiOperation(value = "用户登陆",notes ="用户登陆",httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login( @RequestBody UserBo userBo){
        String username =userBo.getUsername();
        String password =userBo.getPassword();
        //1. 判断用户名和密码必须不为空
        if(StringUtils.isBlank(username)||
                StringUtils.isBlank(password)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        //2.实现注册
        Users users = userService.queryUserForLogin(username,password);
         if(users == null){
             return IMOOCJSONResult.errorMsg("用户名或密码不正确");
         }
        return IMOOCJSONResult.ok(users);
    }

}
