package com.ssaw.config.sdk.config;

import com.ssaw.config.sdk.annotation.EnableConfig;
import com.ssaw.config.sdk.fallback.ConfigFeignImpl;
import feign.Feign;
import feign.Logger;
import okhttp3.ConnectionPool;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author HuSen
 * @date 2019/5/30 17:04
 */
@Configuration
@EnableFeignClients(basePackages = "com.ssaw.config.sdk.feign")
@ConditionalOnBean(annotation = EnableConfig.class)
public class ConfigAutoConfiguration {

    /**
     * NONE:不记录任何信息
     * BASIC:仅记录请求方法，URL以及响应状态码和执行时间
     * HEADERS:除了记录BASIC级别的信息外，还会记录请求和响应的头信息
     * FULL:记录所有请求与响应的明细，包括头信息，请求体，元数据
     *
     * @return 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @ConditionalOnMissingBean(ConfigFeignImpl.class)
    @Bean
    public ConfigFeignImpl configFeignImpl() {
        return new ConfigFeignImpl();
    }

    @Configuration
    @ConditionalOnClass(Feign.class)
    @AutoConfigureBefore(FeignAutoConfiguration.class)
    public static class FeignOkHttpConfig {

        @Bean
        public okhttp3.OkHttpClient okHttpClient() {
            return new okhttp3.OkHttpClient.Builder()
                    // 设置连接超时
                    .connectTimeout(60, TimeUnit.SECONDS)
                    // 设置读超时
                    .readTimeout(60, TimeUnit.SECONDS)
                    // 设置写超时
                    .writeTimeout(60, TimeUnit.SECONDS)
                    // 是否自动重连
                    .retryOnConnectionFailure(true)
                    // 连接池
                    .connectionPool(new ConnectionPool())
                    // 构建OkHttpClient对象
                    .build();
        }
    }
}