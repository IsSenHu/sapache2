package com.ssaw.ssawauthenticatecenterfeign.interceptor;

import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.SimpleUserAttributeVO;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author HuSen
 * @date 2019/4/28 18:00
 */
public class RestTemplateUserContextInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public @Nonnull ClientHttpResponse intercept(HttpRequest request, @Nonnull byte[] body, ClientHttpRequestExecution execution) throws IOException {
        SimpleUserAttributeVO currentUser = UserContextHolder.currentUser();
        request.getHeaders().add("userInfo", JsonUtils.object2JsonString(currentUser));
        return execution.execute(request, body);
    }
}