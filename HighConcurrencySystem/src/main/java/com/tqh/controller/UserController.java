package com.tqh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tqh.model.Address;
import com.tqh.model.User;
import com.tqh.api.UserSerive;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Mcorleon
 * @Date 2019/2/22 10:34
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference(version = "1.0.0")
    UserSerive userService;

    @GetMapping("/getCurrentUser")
    @ApiOperation(value = "获取当前会话下的用户",notes = "根据shiro的subject得到")
    public User getCurrentUser(){
        User user=userService.getCurrentUser();
        return user;
    }

    /**
     *QPS:400
     * 500*10
     */
    @PostMapping("/getAddressByNickName")
    @ApiOperation(value = "根据用户名获取收货地址列表")
    public List<Address> getAddressByNickName(String nickName){
        List<Address> list=userService.getAddressByNickName(nickName);
        return list;
    }
}
