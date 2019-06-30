package com.tqh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqh.model.Result;
import com.tqh.api.UserSerive;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 15:07
 */
@Api(tags = "主页面模块")
@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private static ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UserSerive userService;



    @PostMapping("/login")
    @ApiOperation(value = "登录验证", notes = "使用shiro进行验证,登陆成功后redis缓存用户")
    @ResponseBody
    public Result Login(String uid, String psw, HttpServletRequest request) {
        String loginName = uid;
        String password = psw;
        return userService.userLogin(loginName, password, request);

    }

    @GetMapping("/goods")
    @ApiOperation(value = "进入商品列表页")
    public String goods() {
        return "goods_list";
    }

    @GetMapping("/miaoshaGoods")
    @ApiOperation(value = "进入秒杀商品详情页")
    public String miaoshaGoods() {
        return "miaoshaGoods";
    }

    @GetMapping("/login")
    @ApiOperation(value = "进入登录页面")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    @ApiOperation(value = "无权限访问提示页")
    public String notAuthenticated() {
        return "403";
    }

    @GetMapping("/checkOrder")
    @ApiOperation(value = "进入确认订单页面")
//    @RequiresRoles("vip")
    public String checkOrder() {
        return "checkOrder";
    }

    @GetMapping("/order_list")
    @ApiOperation(value = "进入用户订单列表页")
    public String order_list() {
        return "order_list";
    }


}