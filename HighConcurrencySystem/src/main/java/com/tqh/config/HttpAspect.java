//package com.tqh.config;
//
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//
//@Aspect
//@Component
//public class HttpAspect {
//    private final static Logger logger= LoggerFactory.getLogger(HttpAspect.class);
//
//    @Pointcut("execution(public * com.tqh.controller..*.*(..))")
//    public void log(){
//    }
//
//    @Before("log()")
//    public void doBefore(JoinPoint joinPoint) {
//        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest httpServletRequest=attributes.getRequest();
//        logger.info("url={}",httpServletRequest.getRequestURL());
//        logger.info("method={}",httpServletRequest.getMethod());
//        logger.info("ip={}",httpServletRequest.getRemoteAddr());
//        logger.info("class_method={}",joinPoint.getSignature().getName());
//        logger.info("Args={}", Arrays.toString(joinPoint.getArgs()));
//    }
//    @AfterReturning(returning = "ret", pointcut = "log()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
//        logger.info("RESPONSE : " + ret);
//    }
//}
