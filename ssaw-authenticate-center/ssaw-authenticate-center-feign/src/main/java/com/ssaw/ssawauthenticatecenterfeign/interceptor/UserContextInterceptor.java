package com.ssaw.ssawauthenticatecenterfeign.interceptor;

import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawauthenticatecenterfeign.store.AuthorizeStore;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawauthenticatecenterfeign.util.AuthUtil;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.SimpleUserAttributeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import static com.ssaw.commons.constant.Constants.ResultCodes.FORBIDDEN;

/**
 * @author HuSen
 * @date 2019/4/28 17:19
 */
public class UserContextInterceptor implements HandlerInterceptor {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static final String ERROR_URI = "/error";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // OAUTH2跳过此过滤器
        String xUseAuth = request.getHeader(AuthUtil.AUTH_TYPE_HEADER_NAME);
        if (StringUtils.equalsIgnoreCase(xUseAuth, AuthUtil.OAUTH2_AUTH_TYPE)) {
            return true;
        }

        String uri = request.getRequestURI();

        if (StringUtils.equalsIgnoreCase(uri, ERROR_URI)) {
            return true;
        }

        // 用户必须登录
        SimpleUserAttributeVO currentUser = UserContextHolder.currentUser();
        if (Objects.isNull(currentUser)) {
            response.setHeader("Content-Type", "application/json");
            response.setCharacterEncoding("UTF-8");
            CommonResult<Object> result = CommonResult.createResult(FORBIDDEN, "请登录", null);
            response.getWriter().write(JsonUtils.object2JsonString(result));
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }

        // 获取对应的匹配规则
        String antMatcherCheck = null;
        for (String antMatcher : AuthorizeStore.URL_SCOPE.keySet()) {
            if (antPathMatcher.match(antMatcher, uri)) {
                antMatcherCheck = antMatcher;
                break;
            }
        }

        // 没有找到该URI所对应的匹配规则，说明配置错误
        if (Objects.isNull(antMatcherCheck)) {
            response.setHeader("Content-Type", "application/json");
            response.setCharacterEncoding("UTF-8");
            CommonResult<Object> result = CommonResult.createResult(FORBIDDEN, "系统配置错误，请联系相关人员", null);
            response.getWriter().write(JsonUtils.object2JsonString(result));
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }

        // 找到所需的作用域
        String scope = AuthorizeStore.URL_SCOPE.get(antMatcherCheck);

        // 用户所有的权限
        Set<String> permissions = currentUser.getPermissions();
        // 用户权限为空 禁止
        if (CollectionUtils.isEmpty(permissions)) {
            response.setHeader("Content-Type", "application/json");
            response.setCharacterEncoding("UTF-8");
            CommonResult<Object> result = CommonResult.createResult(FORBIDDEN, "该用户没有任何权限", null);
            response.getWriter().write(JsonUtils.object2JsonString(result));
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }

        // 验证
        if (!permissions.contains(scope)) {
            response.setHeader("Content-Type", "application/json");
            response.setCharacterEncoding("UTF-8");
            CommonResult<Object> result = CommonResult.createResult(FORBIDDEN, "该用户没有该资源的访问权限", null);
            response.getWriter().write(JsonUtils.object2JsonString(result));
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }
        return true;
    }
}