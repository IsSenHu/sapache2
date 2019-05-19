package com.ssaw.ssawauthenticatecenterfeign.interceptor;

import com.alibaba.fastjson.JSON;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawauthenticatecenterfeign.util.AuthUtil;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.SimpleUserAttributeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author HuSen
 * @date 2019/3/1 9:50
 */
@Slf4j
public class SetUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userInfo = null;
        try {
            String userInfoHeader = request.getHeader(AuthUtil.USER_INFO);
            if (StringUtils.isNotBlank(userInfoHeader)) {
                userInfo = URLDecoder.decode(userInfoHeader, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("解析用户信息失败:", e);
        }
        if (StringUtils.isNotBlank(userInfo)) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("设置用户上下文信息:{}", userInfo);
                }
                SimpleUserAttributeVO simpleUserAttributeVO = JSON.parseObject(userInfo, SimpleUserAttributeVO.class);
                UserContextHolder.storeUser(simpleUserAttributeVO);
            } catch (Exception e) {
                log.error("设置用户上下文异常:",  e);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        UserContextHolder.clearUser();
    }
}