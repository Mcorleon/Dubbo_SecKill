package com.tqh.util;

import com.tqh.model.Result;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    private final static Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AuthorizationException.class)
    public Result AuthenticationExceptionHandler(AuthorizationException e){
        logger.error("权限错误",e);
        return  Result.PERMISSION_ERR;
    }

    @ExceptionHandler(UnknownAccountException.class)
    public Result AUnknownAccountExceptionHandler(UnknownAccountException e){
        logger.error("未知用户",e);
        return  Result.UNKONWN_USER_ERR;
    }
    @ExceptionHandler(IncorrectCredentialsException.class)
    public Result IncorrectCredentialsExceptionHandler(IncorrectCredentialsException e){
        logger.error("校验出错",e);
        return  Result.INCORRECT_PSW_ERR;
    }
    @ExceptionHandler(LockedAccountException.class)
    public Result LockedAccountExceptionHandler(LockedAccountException e){
        logger.error("账户已锁定",e);
        return  Result.LOCK_ERR;
    }
    @ExceptionHandler(ExcessiveAttemptsException.class)
    public Result ExcessiveAttemptsExceptionHandler(ExcessiveAttemptsException e){
        logger.error("错误次数过多",e);
        return  Result.EXCESSIVE_ERR;
    }
    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e){
        logger.error("其他错误",e);
        return  Result.OTHER_ERR;
    }



}
