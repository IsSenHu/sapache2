package com.ssaw.ssawauthenticatecenterservice.config.security;

import com.ssaw.ssawauthenticatecenterservice.authentication.filter.MyOauth2ClientAuthenticationProcessingFilter;
import com.ssaw.ssawauthenticatecenterservice.authentication.manager.MyOauth2AuthenticationManager;
import com.ssaw.ssawauthenticatecenterservice.authentication.point.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author HuSen.
 * @date 2018/11/28 10:54.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final RedisTokenStore redisTokenStore;

    private final ClientDetailsService clientDetailsService;

    @Autowired
    public ResourceServerConfig(RedisTokenStore redisTokenStore, ClientDetailsService clientDetailsService) {
        this.redisTokenStore = redisTokenStore;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("SSAW-AUTHENTICATE-CENTER");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/api/authenticate")
            .and().authorizeRequests().anyRequest().authenticated()
            .and().csrf().disable();

        MyOauth2ClientAuthenticationProcessingFilter filter = new MyOauth2ClientAuthenticationProcessingFilter();
        MyOauth2AuthenticationManager manager = new MyOauth2AuthenticationManager();

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore);

        manager.setTokenServices(tokenServices);
        manager.setClientDetailsService(clientDetailsService);

        filter.setAuthenticationManager(manager);
        filter.setAuthenticationEntryPoint(new AuthenticationEntryPointImpl());

        http.addFilterBefore(filter, AbstractPreAuthenticatedProcessingFilter.class);

        //禁用缓存
        http.headers().cacheControl();
    }
}
