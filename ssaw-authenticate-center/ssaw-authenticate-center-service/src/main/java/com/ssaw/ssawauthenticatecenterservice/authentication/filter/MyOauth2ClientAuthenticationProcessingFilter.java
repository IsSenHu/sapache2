package com.ssaw.ssawauthenticatecenterservice.authentication.filter;

import com.google.common.collect.Sets;
import com.ssaw.commons.util.app.ApplicationContextUtil;
import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.security.util.SecurityUtils;
import com.ssaw.ssawauthenticatecenterfeign.properties.EnableResourceAutoProperties;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.SimpleUserAttributeVO;
import com.ssaw.ssawauthenticatecenterservice.authentication.manager.MyOauth2AuthenticationManager;
import com.ssaw.ssawauthenticatecenterservice.details.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.authentication.*;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.ssaw.commons.constant.Constants.ResultCodes.ERROR;
import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;

/**
 * @author hszyp
 */
@Slf4j
public class MyOauth2ClientAuthenticationProcessingFilter implements Filter, InitializingBean {

    private final static Log logger = LogFactory.getLog(OAuth2AuthenticationProcessingFilter.class);

    private AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();

    private MyOauth2AuthenticationManager authenticationManager;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new OAuth2AuthenticationDetailsSource();

    private TokenExtractor tokenExtractor = new BearerTokenExtractor();

    private AuthenticationEventPublisher eventPublisher = new MyOauth2ClientAuthenticationProcessingFilter.NullEventPublisher();

    private boolean stateless = true;

    /**
     * Flag to say that this filter guards stateless resources (default true). Set this to true if the only way the
     * resource can be accessed is with a token. If false then an incoming cookie can populate the security context and
     * allow access to a caller that isn't an OAuth2 client.
     *
     * @param stateless the flag to set (default true)
     */
    public void setStateless(boolean stateless) {
        this.stateless = stateless;
    }

    /**
     * @param authenticationEntryPoint the authentication entry point to set
     */
    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * @param authenticationManager the authentication manager to set (mandatory with no default)
     */
    public void setAuthenticationManager(MyOauth2AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * @param tokenExtractor the tokenExtractor to set
     */
    public void setTokenExtractor(TokenExtractor tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }

    /**
     * @param eventPublisher the event publisher to set
     */
    public void setAuthenticationEventPublisher(AuthenticationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * @param authenticationDetailsSource The AuthenticationDetailsSource to use
     */
    public void setAuthenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.state(authenticationManager != null, "AuthenticationManager is required");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        final boolean debug = logger.isDebugEnabled();
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        String requestUri = request.getParameter("requestUri");

        EnableResourceAutoProperties properties = ApplicationContextUtil.getBean(EnableResourceAutoProperties.class);
        List<String> whiteList = properties.getWhiteList();
        log.info("需要跳过的白名单:{}", whiteList);

        if (whiteList.contains(requestUri)) {
            response.getWriter().write(Objects.requireNonNull(JsonUtils.object2JsonString(CommonResult.createResult(SUCCESS, "白名单无需认证", null))));
            return;
        }

        try {

            CommonResult<String> result;
            Authentication authentication = tokenExtractor.extract(request);

            if (authentication == null) {
                if (stateless && isAuthenticated()) {
                    if (debug) {
                        logger.debug("Clearing security context.");
                    }
                    SecurityContextHolder.clearContext();
                }
                if (debug) {
                    logger.debug("No token in request, will continue chain.");
                }
                result = CommonResult.createResult(ERROR, "没有token信息", null);
            }
            else {
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE, authentication.getPrincipal());
                if (authentication instanceof AbstractAuthenticationToken) {
                    AbstractAuthenticationToken needsDetails = (AbstractAuthenticationToken) authentication;
                    needsDetails.setDetails(authenticationDetailsSource.buildDetails(request));
                }

                Authentication authResult = authenticationManager.authenticate(request, authentication);

                if (debug) {
                    logger.debug("Authentication success: " + authResult);
                }

                eventPublisher.publishAuthenticationSuccess(authResult);

                SecurityContextHolder.getContext().setAuthentication(authResult);

                UserDetailsImpl userDetailsImpl = SecurityUtils.getUserDetails(UserDetailsImpl.class);
                SimpleUserAttributeVO simpleUserAttributeVO = new SimpleUserAttributeVO(userDetailsImpl.getId(), userDetailsImpl.getUsername(), userDetailsImpl.getRealName(), userDetailsImpl.getDescription(),
                        userDetailsImpl.getIsEnable(), userDetailsImpl.getOtherInfo(), Sets.newHashSet());
                result = CommonResult.createResult(SUCCESS, "access", JsonUtils.object2JsonString(simpleUserAttributeVO));
            }
            response.getWriter().write(Objects.requireNonNull(JsonUtils.object2JsonString(result)));
        }
        catch (OAuth2Exception failed) {
            SecurityContextHolder.clearContext();

            if (debug) {
                logger.debug("Authentication request failed: " + failed);
            }
            eventPublisher.publishAuthenticationFailure(new BadCredentialsException(failed.getMessage(), failed),
                    new PreAuthenticatedAuthenticationToken("access-token", "N/A"));

            authenticationEntryPoint.commence(request, response,
                    new InsufficientAuthenticationException(failed.getMessage(), failed));
        }
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    private static final class NullEventPublisher implements AuthenticationEventPublisher {
        @Override
        public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        }

        @Override
        public void publishAuthenticationSuccess(Authentication authentication) {
        }
    }
}
