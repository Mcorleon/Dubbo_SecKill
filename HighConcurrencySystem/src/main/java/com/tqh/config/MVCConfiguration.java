package com.tqh.config;

import com.tqh.util.AccessLimitInterceptor;
import com.tqh.util.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 在mvc配置拦截器
 */
@Configuration
public class MVCConfiguration extends WebMvcConfigurerAdapter{
  @Bean
  public HandlerInterceptor getAccessLimitInterceptor(){
    return new  AccessLimitInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //注册拦截器
    registry.addInterceptor(getAccessLimitInterceptor());
    System.out.println("------------限流拦截器注册完毕---------------");

  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

}