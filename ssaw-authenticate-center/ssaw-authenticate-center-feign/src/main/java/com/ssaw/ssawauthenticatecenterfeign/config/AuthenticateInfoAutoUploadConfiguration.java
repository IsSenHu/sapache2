package com.ssaw.ssawauthenticatecenterfeign.config;

import com.alibaba.fastjson.JSON;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawauthenticatecenterfeign.store.AuthorizeStore;
import com.ssaw.ssawauthenticatecenterfeign.vo.ButtonVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.MenuVO;
import com.ssaw.ssawauthenticatecenterfeign.feign.AuthenticateFeign;
import com.ssaw.ssawauthenticatecenterfeign.properties.EnableResourceAutoProperties;
import com.ssaw.ssawauthenticatecenterfeign.vo.UploadVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.resource.UploadResourceVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * @date 2019/2/26 11:27
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(EnableResourceAutoProperties.class)
@ConditionalOnMissingClass("com.ssaw.ssawauthenticatecenterservice.conditional.ConditionalOnMissingClass")
public class AuthenticateInfoAutoUploadConfiguration {

    private AtomicBoolean init = new AtomicBoolean(false);

    private final EnableResourceAutoProperties enableResourceAutoProperties;

    private final AuthenticateFeign authenticateFeign;

    private final ApplicationContext context;

    @Autowired
    public AuthenticateInfoAutoUploadConfiguration(EnableResourceAutoProperties enableResourceAutoProperties, AuthenticateFeign authenticateFeign, ApplicationContext context) {
        this.enableResourceAutoProperties = enableResourceAutoProperties;
        this.authenticateFeign = authenticateFeign;
        this.context = context;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        if (!init.getAndSet(true)) {
            Assert.hasText(enableResourceAutoProperties.getResourceId(), "资源ID不能为空");
            Assert.hasText(enableResourceAutoProperties.getDescription(), "资源描述不能为空");
            Assert.hasText(enableResourceAutoProperties.getCode(), "资源编码不能为空");

            UploadVO uploadVO = new UploadVO();
            UploadResourceVO uploadResourceVO = setUploadResourceVO(uploadVO);
            Map<String, Object> beansWithAnnotation = setMenuVO(uploadVO);
            List<SecurityMethod> securityMethods = setButtons(uploadVO, beansWithAnnotation);
            setScopes(uploadVO, uploadResourceVO, beansWithAnnotation, securityMethods);
            setWhiteList(uploadVO);
            log.info("开始上传认证信息:{}", JSON.toJSONString(uploadVO));
            CommonResult<UploadVO> commonResult = authenticateFeign.uploadAuthenticateInfo(uploadVO);
            log.info("上传认证信息结果:{}", JSON.toJSONString(commonResult));
        }
    }

    private void setScopes(UploadVO uploadVO, UploadResourceVO uploadResourceVO, Map<String, Object> beansWithAnnotation, List<SecurityMethod> securityMethods) {
        List<ScopeVO> scopeVOList = new ArrayList<>();
        List<Menu> menus = new ArrayList<>();
        for (Object bean : beansWithAnnotation.values()) {
            SecurityApi securityApi = AnnotationUtils.findAnnotation(bean.getClass(), SecurityApi.class);
            if (Objects.nonNull(securityApi)) {
                menus.addAll(Arrays.stream(securityApi.menu()).collect(Collectors.toList()));
            }
            Method[] declaredMethods = bean.getClass().getMethods();
            for (Method method : declaredMethods) {
                SecurityMethod securityMethod = AnnotationUtils.findAnnotation(method, SecurityMethod.class);
                if (Objects.nonNull(securityMethod)) {
                    securityMethods.add(securityMethod);
                    AuthorizeStore.URL_SCOPE.put(securityMethod.antMatcher(), enableResourceAutoProperties.getResourceId().concat("_").concat(securityMethod.scope()));
                }
            }
        }

        Set<Map.Entry<String, List<SecurityMethod>>> entries = securityMethods.stream().collect(Collectors.groupingBy(SecurityMethod::scope)).entrySet();
        for (Map.Entry<String, List<SecurityMethod>> entry : entries) {
            String antMatchers = String.join(",", entry.getValue().stream().map(SecurityMethod::antMatcher).collect(Collectors.toList()).toArray(new String[]{}));
            ScopeVO scopeVO = new ScopeVO();
            scopeVO.setResourceId(uploadResourceVO.getId());
            scopeVO.setResourceName(uploadResourceVO.getResourceId());
            scopeVO.setScope(enableResourceAutoProperties.getResourceId().concat("_").concat(entry.getKey()));
            scopeVO.setUri(antMatchers);
            scopeVOList.add(scopeVO);
        }

        for (Menu menu : menus) {
            ScopeVO scopeVO = new ScopeVO();
            scopeVO.setResourceId(uploadResourceVO.getId());
            scopeVO.setResourceName(uploadResourceVO.getResourceId());
            scopeVO.setScope(enableResourceAutoProperties.getResourceId().concat("_").concat(menu.scope()));
            scopeVO.setUri(UUID.randomUUID().toString());
            scopeVOList.add(scopeVO);
        }
        uploadVO.setScopeVOList(scopeVOList);
    }

    private void setWhiteList(UploadVO uploadVO) {
        List<String> whiteList = enableResourceAutoProperties.getWhiteList();
        uploadVO.setWhiteList(whiteList);
    }

    private List<SecurityMethod> setButtons(UploadVO uploadVO, Map<String, Object> beansWithAnnotation) {
        List<SecurityMethod> securityMethods = new ArrayList<>();
        for (Object bean : beansWithAnnotation.values()) {
            Method[] declaredMethods = bean.getClass().getMethods();
            for (Method method : declaredMethods) {
                SecurityMethod securityMethod = AnnotationUtils.findAnnotation(method, SecurityMethod.class);
                if (Objects.nonNull(securityMethod) && StringUtils.isNotBlank(securityMethod.button())) {
                    securityMethods.add(securityMethod);
                }
            }
        }
        List<ButtonVO> buttonVOS = securityMethods.stream().map(securityMethod -> {
            ButtonVO buttonVO = new ButtonVO();
            buttonVO.setKey(securityMethod.button());
            buttonVO.setScope(enableResourceAutoProperties.getResourceId().concat("_").concat(securityMethod.scope()));
            buttonVO.setName(securityMethod.buttonName());
            return buttonVO;
        }).collect(Collectors.toList());
        uploadVO.setButtonVOS(buttonVOS);
        return securityMethods;
    }

    private Map<String, Object> setMenuVO(UploadVO uploadVO) {
        MenuVO menuVO = new MenuVO();
        menuVO.setIndex(enableResourceAutoProperties.getCode());
        menuVO.setTemplate(new MenuVO.Template(enableResourceAutoProperties.getIcon(), enableResourceAutoProperties.getDescription()));
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(SecurityApi.class);
        List<SecurityApi> securityApiList = beansWithAnnotation.values().stream().map(o -> o.getClass().getAnnotation(SecurityApi.class)).collect(Collectors.toList());
        List<MenuVO> menuVOItems = securityApiList.stream().collect(Collectors.groupingBy(securityApi -> securityApi.index().concat("_").concat(securityApi.group()))).entrySet().stream().map(entry -> {
            String[] s = entry.getKey().split("_");
            MenuVO m1 = new MenuVO();
            m1.setIndex(enableResourceAutoProperties.getCode().concat("-").concat(s[0]));
            m1.setTemplate(new MenuVO.Template("", s[1]));
            List<MenuVO> items = new ArrayList<>();
            entry.getValue().stream().map(SecurityApi::menu).forEach(menus -> Arrays.stream(menus).forEach(m -> {
                MenuVO m2 = new MenuVO();
                m2.setTitle(m.title());
                m2.setIndex(enableResourceAutoProperties.getCode().concat("-").concat(m.index()));
                m2.setScope(enableResourceAutoProperties.getResourceId().concat("_").concat(m.scope()));
                m2.setTo(m.to());
                items.add(m2);
            }));
            m1.setItems(items);
            return m1;
        }).collect(Collectors.toList());
        menuVO.setItems(menuVOItems);
        uploadVO.setMenuVO(menuVO);
        return beansWithAnnotation;
    }

    private UploadResourceVO setUploadResourceVO(UploadVO uploadVO) {
        UploadResourceVO uploadResourceVO = new UploadResourceVO();
        uploadResourceVO.setResourceId(enableResourceAutoProperties.getResourceId());
        uploadResourceVO.setDescription(enableResourceAutoProperties.getDescription());
        uploadVO.setUploadResourceVO(uploadResourceVO);
        return uploadResourceVO;
    }
}