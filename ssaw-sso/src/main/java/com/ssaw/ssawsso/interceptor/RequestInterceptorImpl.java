package com.ssaw.ssawsso.interceptor;

import com.ssaw.commons.properties.FeignHeaderProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author HuSen.
 * @date 2018/12/5 9:59.
 */
@Component
public class RequestInterceptorImpl implements RequestInterceptor {

    private final FeignHeaderProperties properties;

    @Autowired
    public RequestInterceptorImpl(FeignHeaderProperties properties) {
        this.properties = properties;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "bearer " + properties.getBearerToken());
    }
}
