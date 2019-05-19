package com.ssaw.ssawmehelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author HuSen
 * @date 2019/4/13 10:27
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "commitOverTimeExecutor")
    public ThreadPoolTaskExecutor commitOverTimeExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        // 最大线程数
        taskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        // 线程池所使用的缓冲队列大小
        taskExecutor.setQueueCapacity(50);
        // 允许空闲的时间
        taskExecutor.setKeepAliveSeconds(200);
        // 对拒绝task的处理策略 直接在调用线程中运行被拒绝的任务
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化线程池
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean(name = "commitLeaveExecutor")
    public ThreadPoolTaskExecutor commitLeaveExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        // 最大线程数
        taskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        // 线程池所使用的缓冲队列大小
        taskExecutor.setQueueCapacity(50);
        // 允许空闲的时间
        taskExecutor.setKeepAliveSeconds(200);
        // 对拒绝task的处理策略 直接在调用线程中运行被拒绝的任务
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化线程池
        taskExecutor.initialize();
        return taskExecutor;
    }
}