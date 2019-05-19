package com.ssaw.ssawauthenticatecenterfeign.config;

import com.ssaw.ssawauthenticatecenterfeign.annotations.EnableAuthenticateFeign;
import com.ssaw.ssawauthenticatecenterfeign.fallback.AuthenticateFeignFallback;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author HuSen
 * @date 2019/4/28 22:01
 */
@EnableFeignClients(basePackages = "com.ssaw.ssawauthenticatecenterfeign.feign")
@ConditionalOnBean(annotation = EnableAuthenticateFeign.class)
public class FeignAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AuthenticateFeignFallback.class)
    public AuthenticateFeignFallback authenticateFeignFallback() {
        return new AuthenticateFeignFallback();
    }
}