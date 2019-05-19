package com.ssaw.ssawauthenticatecenterfeign.config;

import com.ssaw.ssawauthenticatecenterfeign.interceptor.RestTemplateUserContextInterceptor;
import com.ssaw.ssawauthenticatecenterfeign.interceptor.SetUserInfoInterceptor;
import com.ssaw.ssawauthenticatecenterfeign.interceptor.UserContextInterceptor;
import com.ssaw.ssawauthenticatecenterfeign.properties.EnableResourceAutoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author HuSen
 * @date 2019/3/1 10:03
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final EnableResourceAutoProperties e;

    @Autowired
    public InterceptorConfig(EnableResourceAutoProperties e) {
        this.e = e;
    }

    /**
     * 添加拦截器
     *
     * @param registry 拦截注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SetUserInfoInterceptor())
                .addPathPatterns("/**").order(Integer.MIN_VALUE)
                .excludePathPatterns(e.getSystemWhiteList()).excludePathPatterns(e.getStaticResourceList());
        registry.addInterceptor(new UserContextInterceptor())
                .addPathPatterns("/**").order(Integer.MIN_VALUE + 1)
                .excludePathPatterns(e.getWhiteList()).excludePathPatterns(e.getSystemWhiteList()).excludePathPatterns(e.getStaticResourceList());
    }

    /**
     * RestTemplate 拦截器，在发送请求前设置鉴权的用户上下文信息
     *
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new RestTemplateUserContextInterceptor());
        return restTemplate;
    }
}