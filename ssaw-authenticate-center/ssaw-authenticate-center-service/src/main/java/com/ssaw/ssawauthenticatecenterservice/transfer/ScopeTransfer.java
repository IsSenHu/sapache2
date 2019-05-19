package com.ssaw.ssawauthenticatecenterservice.transfer;

import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.resource.ResourceEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.scope.ScopeEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * @author HuSen.
 * @date 2018/12/13 12:56.
 */
@Component
public class ScopeTransfer {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ScopeTransfer(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public ScopeEntity dto2Entity(ScopeVO scopeVO) {
        ScopeEntity entity = null;
        if (scopeVO != null) {
            entity = new ScopeEntity();
            entity.setId(scopeVO.getId());
            entity.setUri(scopeVO.getUri());
            entity.setScope(scopeVO.getScope());
            entity.setResourceId(scopeVO.getResourceId());
        }
        return entity;
    }

    public ScopeVO entity2Dto(ScopeEntity scopeEntity) {
        ScopeVO dto = new ScopeVO();
        if(scopeEntity != null) {
            dto = new ScopeVO();
            Optional<ResourceEntity> optional = resourceRepository.findById(scopeEntity.getResourceId());
            if(optional.isPresent()) {
                dto.setResourceName(optional.get().getResourceId());
            }
            dto.setId(scopeEntity.getId());
            dto.setScope(scopeEntity.getScope());
            dto.setUri(scopeEntity.getUri());
            dto.setResourceId(scopeEntity.getResourceId());
            dto.setCreateTime(scopeEntity.getCreateTime());
            dto.setCreateMan(scopeEntity.getCreateMan());
            dto.setModifyTime(scopeEntity.getModifyTime());
            dto.setModifyMan(scopeEntity.getModifyMan());
        }
        return dto;
    }

    public ScopeVO entity2DtoNotGetResourceName(ScopeEntity scopeEntity) {
        ScopeVO dto = new ScopeVO();
        if(scopeEntity != null) {
            dto = new ScopeVO();
            dto.setId(scopeEntity.getId());
            dto.setScope(scopeEntity.getScope());
            dto.setUri(scopeEntity.getUri());
            dto.setResourceId(scopeEntity.getResourceId());
            dto.setCreateTime(scopeEntity.getCreateTime());
            dto.setCreateMan(scopeEntity.getCreateMan());
            dto.setModifyTime(scopeEntity.getModifyTime());
            dto.setModifyMan(scopeEntity.getModifyMan());
        }
        return dto;
    }
}
