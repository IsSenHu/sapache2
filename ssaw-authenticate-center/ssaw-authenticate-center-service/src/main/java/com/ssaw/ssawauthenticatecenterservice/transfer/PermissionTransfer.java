package com.ssaw.ssawauthenticatecenterservice.transfer;

import com.ssaw.ssawauthenticatecenterfeign.vo.permission.PermissionVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.permission.PermissionEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.resource.ResourceRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.scope.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 * @author HuSen.
 * @date 2018/12/13 17:10.
 */
@Component
public class PermissionTransfer {

    private final ScopeRepository scopeRepository;

    private final ResourceRepository resourceRepository;

    @Autowired
    public PermissionTransfer(ScopeRepository scopeRepository, ResourceRepository resourceRepository) {
        this.scopeRepository = scopeRepository;
        this.resourceRepository = resourceRepository;
    }

    public PermissionEntity dto2Entity(PermissionVO dto) {
        PermissionEntity entity = null;
        if(null != dto) {
            entity = new PermissionEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setResourceId(dto.getResourceId());
            entity.setScopeId(dto.getScopeId());
        }
        return entity;
    }

    public PermissionVO entity2Dto(PermissionEntity entity, boolean details) {
        PermissionVO dto = new PermissionVO();
        if(null != entity) {
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setCreateMan(entity.getCreateMan());
            dto.setCreateTime(entity.getCreateTime());
            dto.setModifyMan(entity.getModifyMan());
            dto.setModifyTime(entity.getModifyTime());
            dto.setScopeId(entity.getScopeId());
            if(details && !Objects.isNull(entity.getScopeId())) {
                scopeRepository.findById(entity.getScopeId()).ifPresent(scope -> dto.setScopeName(scope.getScope()));
            }
            dto.setResourceId(entity.getResourceId());
            if(details && !Objects.isNull(entity.getResourceId())) {
                resourceRepository.findById(entity.getResourceId()).ifPresent(resource -> dto.setResourceName(resource.getResourceId()));
            }
        }
        return dto;
    }

    public PermissionVO simpleEntity2Dto(PermissionEntity entity) {
        PermissionVO dto = null;
        if(null != entity) {
            dto = new PermissionVO();
            dto.setId(entity.getScopeId());
            dto.setName(entity.getName());
        }
        return dto;
    }

}
