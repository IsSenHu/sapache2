package com.ssaw.ssawauthenticatecenterservice.listen;

import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.scope.ScopeEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.scope.ScopeRepository;
import com.ssaw.ssawauthenticatecenterservice.transfer.ScopeTransfer;
import com.ssaw.ssawauthenticatecenterservice.authentication.cache.CacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HuSen.
 * @date 2019/1/23 11:33.
 */
@Slf4j
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private final ScopeRepository scopeRepository;
    private final ScopeTransfer scopeTransfer;

    @Autowired
    public ApplicationStartup(ScopeRepository scopeRepository, ScopeTransfer scopeTransfer) {
        this.scopeRepository = scopeRepository;
        this.scopeTransfer = scopeTransfer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("init scope cache...");
        List<ScopeEntity> entities = scopeRepository.findAll();
        if(CollectionUtils.isNotEmpty(entities)) {
            entities.stream().map(scopeTransfer::entity2Dto).collect(Collectors.groupingBy(ScopeVO::getResourceName))
                    .forEach(CacheManager::refreshScopes);
            log.info("init scope caches:{}", JsonUtils.object2JsonString(entities));
        }
    }
}
