package com.tqh.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqh.mapper.UserMapper;
import com.tqh.model.Address;
import com.tqh.model.Result;
import com.tqh.model.User;
import com.tqh.api.UserSerive;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 16:35
 */
@Service(version = "1.0.0")
@org.springframework.stereotype.Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserSerive {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Override
    @Cacheable(key ="'userInfo'+#p0")
    public User findUserByName(String name) {
        return userMapper.findByLoginName(name);
    }

    @Override
    public Result userLogin(String id, String psw,HttpServletRequest request) {
        String loginName =id;
        String password=psw;
        logger.info("准备登陆用户 => {}", loginName);
        UsernamePasswordToken token = new UsernamePasswordToken(loginName,password);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
        //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
        //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
        logger.info("对用户[" + loginName + "]进行登录验证..验证开始");
        currentUser.login(token);
        logger.info("对用户[" + loginName + "]进行登录验证..验证通过");

        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            String sessionID=currentUser.getSession().getId().toString();
            logger.info("用户[" + loginName + "]登录认证通过"+" sessionID:"+sessionID);
            //登陆成功后redis缓存用户信息
            try {
                User user=userMapper.findByLoginName(loginName);
                stringRedisTemplate.opsForValue().set(sessionID,MAPPER.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            stringRedisTemplate.expire(sessionID,60, TimeUnit.MINUTES);
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            // 获取保存的URL
            if (savedRequest == null || savedRequest.getRequestUrl() == null||savedRequest.getRequestUrl().equals("/favicon.ico")) {
                return new Result(200,"/goods");
            }
            return new Result(200,savedRequest.getRequestUrl());
        } else {
            token.clear();
            return Result.PERMISSION_ERR;
        }
    }

    @Override
    public User getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        String obj=stringRedisTemplate.opsForValue().get(currentUser.getSession().getId().toString());
        if(StringUtils.isEmpty(obj)){
            return null;
        }
        User user=null;
        try {
            user=MAPPER.readValue(obj,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    @Cacheable(key ="'addressList'+#p0")
    public List<Address> getAddressByNickName(String nickName) {
        System.out.println("查询地址");
        return userMapper.getAddressByNickName(nickName);
    }

    @Override
    @Cacheable(key ="'addressInfo'+#p0")
    public Address getAddressByAddressID(String address_id) {
        return userMapper.getAddressByAddressID(address_id);
    }


}
