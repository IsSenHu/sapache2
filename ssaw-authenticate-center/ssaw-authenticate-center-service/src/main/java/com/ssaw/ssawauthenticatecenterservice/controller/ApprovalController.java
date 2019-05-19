package com.ssaw.ssawauthenticatecenterservice.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自定义授权页面
 * @author HuSen
 * @date 2019/4/29 20:32
 */
@RestController
@SessionAttributes("authorizationRequest")
public class ApprovalController {

    @GetMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        String clientId = authorizationRequest.getClientId();

        CsrfToken csrfToken = (CsrfToken) (model.containsKey("_csrf") ? model.get("_csrf") : request.getAttribute("_csrf"));
        @SuppressWarnings("unchecked")
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ?
                model.get("scopes") : request.getAttribute("scopes"));
        Map<String, List<String>> collect = scopes.keySet().stream()
                .collect(Collectors.groupingBy(s -> StringUtils.substringBetween(s, ".", "_")));

        Map<String, Map<String, String>> scopeView = new HashMap<>(collect.size());
        for (Map.Entry<String, List<String>> entry : collect.entrySet()) {
            Map<String, String> scopeValue = new HashMap<>(entry.getValue().size());
            for (String key : entry.getValue()) {
                scopeValue.put(key, scopes.get(key));
            }
            scopeView.put(entry.getKey(), scopeValue);
        }
        return new ModelAndView("approval")
                .addObject("clientId", clientId)
                .addObject("csrfToken", csrfToken)
                .addObject("scopes", scopeView);
    }
}