package com.ssaw.ssawauthenticatecenterservice.config.upload;

import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawauthenticatecenterfeign.event.local.AppFinishedEvent;
import com.ssaw.ssawauthenticatecenterfeign.event.local.UploadScopeAndWhiteListFinishedEvent;
import com.ssaw.ssawauthenticatecenterfeign.store.AuthorizeStore;
import com.ssaw.ssawauthenticatecenterfeign.vo.resource.UploadResourceVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.properties.EnableResourceAutoProperties;
import com.ssaw.ssawauthenticatecenterservice.service.MenuService;
import com.ssaw.ssawauthenticatecenterservice.service.ResourceService;
import com.ssaw.ssawauthenticatecenterservice.service.ScopeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;

/**
 * @author HuSen
 * @date 2019/2/25 17:41
 */
@Slf4j
@Configuration
@EnableScheduling
@EnableConfigurationProperties(EnableResourceAutoProperties.class)
public class ResourceAutoConfig  {

    private AtomicBoolean initialized = new AtomicBoolean(false);

    private final ApplicationContext context;

    private final EnableResourceAutoProperties enableResourceAutoProperties;

    private final ResourceService resourceService;

    private final ScopeService scopeService;

    private final MenuService menuService;

    @Autowired
    public ResourceAutoConfig(EnableResourceAutoProperties enableResourceAutoProperties, ApplicationContext context, ResourceService resourceService, ScopeService scopeService, MenuService menuService) {
        this.enableResourceAutoProperties = enableResourceAutoProperties;
        this.context = context;
        this.resourceService = resourceService;
        this.scopeService = scopeService;
        this.menuService = menuService;
    }
    
    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        if (!initialized.getAndSet(true)) {
            Assert.hasText(enableResourceAutoProperties.getResourceId(), "资源ID不能为空");
            Assert.hasText(enableResourceAutoProperties.getDescription(), "资源描述不能为空");
            Assert.hasText(enableResourceAutoProperties.getCode(), "资源编码不能为空");

            UploadResourceVO resource = new UploadResourceVO();
            resource.setResourceId(enableResourceAutoProperties.getResourceId());
            resource.setDescription(enableResourceAutoProperties.getDescription());
            log.info("上传资源服务:{}", JsonUtils.object2JsonString(resource));
            CommonResult<UploadResourceVO> result = resourceService.uploadResource(resource);
            Assert.state(result.getCode() == SUCCESS, "上传资源服务失败");

            List<SecurityMethod> securityMethods = new ArrayList<>();
            List<ScopeVO> scopeVOList = new ArrayList<>();
            List<Menu> menus = new ArrayList<>();
            Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(SecurityApi.class);
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
                scopeVO.setResourceId(result.getData().getId());
                scopeVO.setResourceName(result.getData().getResourceId());
                scopeVO.setScope(enableResourceAutoProperties.getResourceId().concat("_").concat(entry.getKey()));
                scopeVO.setUri(antMatchers);
                scopeVOList.add(scopeVO);
            }

            for (Menu menu : menus) {
                ScopeVO scopeVO = new ScopeVO();
                scopeVO.setResourceId(result.getData().getId());
                scopeVO.setResourceName(result.getData().getResourceId());
                scopeVO.setScope(enableResourceAutoProperties.getResourceId().concat("_").concat(menu.scope()));
                scopeVO.setUri(UUID.randomUUID().toString());
                scopeVOList.add(scopeVO);
            }

            log.info("上传作用域:{}", JsonUtils.object2JsonString(scopeVOList));
            CommonResult<String> commonResult = scopeService.uploadScopes(result.getData().getId(), scopeVOList);
            Assert.state(commonResult.getCode() == SUCCESS, "上传作用域失败");

            List<String> whiteList = enableResourceAutoProperties.getWhiteList();
            log.info("上传白名单:{}", JsonUtils.object2JsonString(whiteList));
            CommonResult<String> uploadWhiteListResult = menuService.uploadWhiteList(whiteList, enableResourceAutoProperties.getResourceId());
            Assert.state(uploadWhiteListResult.getCode() == SUCCESS, "上传白名单失败");

            context.publishEvent(new UploadScopeAndWhiteListFinishedEvent(enableResourceAutoProperties.getResourceId()));
        }
    }

    @EventListener(UploadScopeAndWhiteListFinishedEvent.class)
    public void refreshScopes(UploadScopeAndWhiteListFinishedEvent event) {
        String resourceId = (String) event.getSource();
        log.info("因为:{} 完成认证信息上传, 重新刷新作用域和内部用户信息", resourceId);
        scopeService.refreshScope(resourceId);
        context.publishEvent(new AppFinishedEvent(resourceId));
    }
}