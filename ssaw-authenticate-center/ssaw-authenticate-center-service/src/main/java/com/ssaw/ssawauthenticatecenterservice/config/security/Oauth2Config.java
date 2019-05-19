package com.ssaw.ssawauthenticatecenterservice.config.security;

import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.ssawauthenticatecenterservice.service.ClientService;
import com.ssaw.ssawauthenticatecenterservice.service.UserService;
import com.ssaw.ssawauthenticatecenterservice.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Map;

/**
 * @author HuSen.
 * @date 2018/11/28 10:53.
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    public static final String SCOPE_CACHE_KEY = "scope_cache_key";

    private final AuthenticationManager authenticationManager;
    private final RedisConnectionFactory redisConnectionFactory;
    private final UserService userService;
    private final ClientService clientService;

    @Autowired
    public Oauth2Config(AuthenticationManager authenticationManager, RedisConnectionFactory redisConnectionFactory, UserService userService, ClientService clientService) {
        this.authenticationManager = authenticationManager;
        this.redisConnectionFactory = redisConnectionFactory;
        this.userService = userService;
        this.clientService = clientService;
    }

    private static final String KEY_PAIR = "myauthenticatecenter";
    private static final String MY_PASS = "521428Slyp";
    private static final String KEY_STORE_PATH = "keystore.jks";

    /**
     * @return 使用非对称加密算法来对Token进行签名
     */
    @Bean
    public JwtAccessTokenConverter getJwtAccessTokenConverter() {
        final JwtAccessToken converter = new JwtAccessToken();
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource(KEY_STORE_PATH), MY_PASS.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(KEY_PAIR));
        return converter;
    }

    /**
     * @return 使用Redis来存Token
     */
    @Bean
    public RedisTokenStore getRedisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * @param security 用来配置令牌端点(Token Endpoint)的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .realm("SSAW-AUTHENTICATE-CENTER")
                // 主要是让/oauth/token支持client_id以及client_secret作登录认证 要进行client校验就必须配置这个
                .allowFormAuthenticationForClients();
    }

    /**
     * @param endpoints 用来配置授权(authorization)以及令牌(token)的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                // 配置JwtAccessToken转换器
                .accessTokenConverter(getJwtAccessTokenConverter())
                // 配置TokenTore
                .tokenStore(getRedisTokenStore())
                // 配置UserDetailsServices
                .userDetailsService(userService)
                // 允许使用Get和Post方法访问端口
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * 配置Client
     * @param clients ClientDetailsServiceConfigurer
     * @throws Exception Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);
    }

    /**
     * 自定义JwtToken转换器
     * @author HuSen
     */
    public class JwtAccessToken extends JwtAccessTokenConverter {

        JwtAccessToken() {
            super();
        }

        /**
         * 生成token
         * @param accessToken accessToken
         * @param authentication authentication
         * @return 生成的token
         */
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
            // 设置额外的用户信息
            UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
            userDetailsImpl.setPassword(null);
            // 将用户信息添加到token额外信息中
            result.getAdditionalInformation().put("user_info", userDetailsImpl);
            return super.enhance(result, authentication);
        }

        /**
         * 解析token
         * @param value tokenValue
         * @param map map
         * @return OAuth2AccessToken
         */
        @Override
        public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
            OAuth2AccessToken oAuth2AccessToken = super.extractAccessToken(value, map);
            convertData(oAuth2AccessToken, map);
            return oAuth2AccessToken;
        }

        /**
         * 数据转换
         * @param oAuth2AccessToken oAuth2AccessToken
         * @param map 额外信息
         */
        private void convertData(OAuth2AccessToken oAuth2AccessToken, Map<String, ?> map) {
            oAuth2AccessToken.getAdditionalInformation().put("user_info", convertUserData(map.get("user_info")));
        }

        /**
         * 获取用户数据
         * @param map 用户数据
         * @return UserDetailsImpl
         */
        private UserDetailsImpl convertUserData(Object map) {
            return JsonUtils.jsonString2Object(JsonUtils.object2JsonString(map), UserDetailsImpl.class);
        }

        @Override
        public void setAccessTokenConverter(AccessTokenConverter tokenConverter) {
            super.setAccessTokenConverter(tokenConverter);
        }

        @Override
        public AccessTokenConverter getAccessTokenConverter() {
            return super.getAccessTokenConverter();
        }

        @Override
        public JwtClaimsSetVerifier getJwtClaimsSetVerifier() {
            return super.getJwtClaimsSetVerifier();
        }

        @Override
        public void setJwtClaimsSetVerifier(JwtClaimsSetVerifier jwtClaimsSetVerifier) {
            super.setJwtClaimsSetVerifier(jwtClaimsSetVerifier);
        }

        @Override
        public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
            return super.convertAccessToken(token, authentication);
        }

        @Override
        public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
            return super.extractAuthentication(map);
        }

        @Override
        public void setVerifier(SignatureVerifier verifier) {
            super.setVerifier(verifier);
        }

        @Override
        public void setSigner(Signer signer) {
            super.setSigner(signer);
        }

        @Override
        public Map<String, String> getKey() {
            return super.getKey();
        }

        @Override
        public void setKeyPair(KeyPair keyPair) {
            super.setKeyPair(keyPair);
        }

        @Override
        public void setSigningKey(String key) {
            super.setSigningKey(key);
        }

        @Override
        public boolean isPublic() {
            return super.isPublic();
        }

        @Override
        public void setVerifierKey(String key) {
            super.setVerifierKey(key);
        }

        @Override
        public boolean isRefreshToken(OAuth2AccessToken token) {
            return super.isRefreshToken(token);
        }

        @Override
        protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            return super.encode(accessToken, authentication);
        }

        @Override
        protected Map<String, Object> decode(String token) {
            return super.decode(token);
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            super.afterPropertiesSet();
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }
}
