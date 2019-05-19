package com.ssaw.uaa;

import com.ssaw.ssawauthenticatecenterfeign.annotations.EnableAuthenticateFeign;
import com.ssaw.uaa.store.TokenStore;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuSen
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableAuthenticateFeign
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * 使用Feign时会执行该拦截器 添加请求头
     */
    @Configuration
    public class FeignConfiguration implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate requestTemplate) {
            String token = TokenStore.current();
            requestTemplate.header("Authorization", token);
        }
    }
}
