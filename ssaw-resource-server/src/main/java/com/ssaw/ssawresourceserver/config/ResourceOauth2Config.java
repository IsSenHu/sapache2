package com.ssaw.ssawresourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @author HuSen.
 * @date 2018/11/26 10:44.
 */
@Configuration
@EnableResourceServer
public class ResourceOauth2Config extends ResourceServerConfigurerAdapter {

    @Primary
    @Bean
    public ResourceServerTokenServices getRemoteTokenServices() {
        return new ResourceServerTokenServices() {

            @Override
            public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
                System.out.println("loadAuthentication:" + accessToken);
                return null;
            }

            @Override
            public OAuth2AccessToken readAccessToken(String accessToken) {
                System.out.println("readAccessToken:" + accessToken);
                return null;
            }
        };
//        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:11001/oauth/check_token");
//        remoteTokenServices.setClientId("admin");
//        remoteTokenServices.setClientSecret("admin");
//        remoteTokenServices.setTokenName("token");
//        return remoteTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(getRemoteTokenServices()).resourceId("SSAW-RESOURCE-SERVER");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
