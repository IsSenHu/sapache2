package com.ssaw.ssawauthenticatecenterservice.authentication.manager;

import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.client.ClientDetailsEntity;
import com.ssaw.ssawauthenticatecenterservice.authentication.cache.CacheManager;
import com.ssaw.ssawauthenticatecenterservice.details.UserDetailsImpl;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 自定义Oauth2AuthenticationManager 使其支持根据请求url判断对应的resourceId和需要的Scope
 * @author hszyp
 * @date 2019/01/25
 */
@Slf4j
@Setter
public class MyOauth2AuthenticationManager implements AuthenticationManager, InitializingBean {

    private ResourceServerTokenServices tokenServices;

    private ClientDetailsService clientDetailsService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() {
        Assert.state(tokenServices != null, "TokenServices are required");
        Assert.state(clientDetailsService != null, "ClientDetailService are required");
        Assert.state(antPathMatcher != null, "AntPathMatcher are required");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null) {
            throw new InvalidTokenException("Invalid token (token not found)");
        }
        String token = (String) authentication.getPrincipal();
        OAuth2Authentication auth = tokenServices.loadAuthentication(token);
        if (auth == null) {
            throw new InvalidTokenException("Invalid token: " + token);
        }
        Collection<String> resourceIds = auth.getOAuth2Request().getResourceIds();

        if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            // Guard against a cached copy of the same details
            if (!details.equals(auth.getDetails())) {
                // Preserve the authentication details from the one loaded by token services
                details.setDecodedDetails(auth.getDetails());
            }
        }
        auth.setDetails(authentication.getDetails());
        auth.setAuthenticated(true);
        return auth;
    }

    public Authentication authenticate(HttpServletRequest request, Authentication authentication) {
        Authentication authenticate = authenticate(authentication);
        // 先设置为false 如果在最后校验通过了在设置为true
        authenticate.setAuthenticated(false);
        OAuth2Authentication auth = (OAuth2Authentication) authenticate;
        // 校验请求uri是否可被该token持有者访问
        String requestUri = request.getParameter("requestUri");

        // requestUri必传，如果不传则授权失败
        if(StringUtils.isBlank(requestUri)) {
            throw new OAuth2AccessDeniedException("requestUri are required");
        }

        List<ScopeVO> scopes = CacheManager.getScopes();
        ScopeVO areRequiredScope = null;
        first : for(ScopeVO scope : scopes) {
            String[] split = scope.getUri().split(",");
            for (String u : split) {
                if(antPathMatcher.match(u, requestUri)) {
                    areRequiredScope = scope;
                    break first;
                }
            }
        }
        String needToValidatingScope = null;

        // 没有找到该请求uri所匹配的scope 说明该uri不被拦截，只有当areRequiredScope不为空的时候才需要校验
        if(!Objects.isNull(areRequiredScope)) {
            String resourceId = areRequiredScope.getResourceName();
            // resourceId为空 不校验 默认为该uri资源不属于任何一个资源服务
            Set<String> tokenResourceIds = auth.getOAuth2Request().getResourceIds();
            if(null != resourceId && !CollectionUtils.isEmpty(tokenResourceIds) && !tokenResourceIds.contains(resourceId)) {
                throw new OAuth2AccessDeniedException("Invalid token does not contain resource id(" + resourceId + ")");
            }

            needToValidatingScope = areRequiredScope.getScope();
        }

        // 设置用户上下文
        String token = (String) authentication.getPrincipal();
        OAuth2AccessToken oAuth2AccessToken = tokenServices.readAccessToken(token);
        UserDetailsImpl user = (UserDetailsImpl) oAuth2AccessToken.getAdditionalInformation().get("user_info");
        // 如果token所带的用户信息为空，则不合法
        if(Objects.isNull(user)) {
            throw new OAuth2AccessDeniedException("Invalid token (" + token  + ")");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 设置是否已被认证为已认证，并设置SecurityContextHolder上下文
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        ClientDetailsEntity client;
        String clientId = auth.getOAuth2Request().getClientId();
        try {
            client = (ClientDetailsEntity) clientDetailsService.loadClientByClientId(clientId);
        } catch (ClientRegistrationException e) {
            throw new OAuth2AccessDeniedException("Invalid token contains invalid client id");
        }

        Set<String> allowed = client.getScope();
        Set<String> tokenScopes = auth.getOAuth2Request().getScope();

        // 如果needToValidatingScope不为空，说明此uri需要被校验
        boolean accessed = StringUtils.isNotBlank(needToValidatingScope) && (!tokenScopes.contains(needToValidatingScope) || !allowed.contains(needToValidatingScope));
        if(accessed) {
            throw new OAuth2AccessDeniedException("Uri (" + requestUri + ") is not permit this token to reach");
        }
        // 校验token的scope权限和客户端的scope权限是否合法
        for(String tokenScope : tokenScopes) {
            if(!allowed.contains(tokenScope)) {
                throw new OAuth2AccessDeniedException("Invalid token contains disallowed scope (" + tokenScope + ") for this client");
            }
        }
        auth.setAuthenticated(true);
        return auth;
    }
}
