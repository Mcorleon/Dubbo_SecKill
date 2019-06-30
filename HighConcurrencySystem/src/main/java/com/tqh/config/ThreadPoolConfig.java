package com.tqh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * @Author Mcorleon
 * @Date 2019/5/25 21:20
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolExecutor executor=new ThreadPoolExecutor(20,50,
                60, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1000),
                Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
