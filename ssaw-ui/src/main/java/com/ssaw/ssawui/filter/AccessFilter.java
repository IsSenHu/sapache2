package com.ssaw.ssawui.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawauthenticatecenterfeign.feign.AuthenticateFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;

/**
 * 认证过滤器
 * @author HuSen
 * @date 2019/02/14
 */
@Slf4j
@Component
public class AccessFilter extends ZuulFilter {

    private final AuthenticateFeign authenticateFeign;

    @Autowired
    public AccessFilter(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") AuthenticateFeign authenticateFeign) {
        this.authenticateFeign = authenticateFeign;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        String serviceId = StringUtils.substringBetween(requestURI, "/", "/");
        String url = StringUtils.substring(requestURI, "/".concat(serviceId).length());
        String prefix = request.getMethod() + "send [" + requestURI + "] request to serviceId [" + serviceId + "] url [" + url + "]";
        CommonResult<String> result = authenticateFeign.authenticate(url);
        String suffix = "认证结果:" + result.getCode() + ",{" + result.getMessage() + "},{" + result.getData() + "}";
        log.info(prefix + suffix);
        if(result.getCode() != SUCCESS) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                HttpServletResponse response = ctx.getResponse();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write(JsonUtils.object2JsonString(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ctx.addZuulRequestHeader("userInfo", result.getData());
        return null;
    }
}
