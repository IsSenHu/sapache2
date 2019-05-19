package com.ssaw.ssawauthenticatecenterservice.authentication.point;

import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.commons.vo.CommonResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import static com.ssaw.commons.constant.Constants.ResultCodes.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权未通过处理
 * @author HuSen
 * @date 2019/01/25
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        CommonResult<String> result;
        if (e instanceof BadCredentialsException) {
            result = CommonResult.createResult(ERROR, "身份认证未通过!", e.getMessage());
        } else {
            result = CommonResult.createResult(ERROR, "forbidden", e.getMessage());
        }
        response.getWriter().write(JsonUtils.object2JsonString(result));
    }
}
