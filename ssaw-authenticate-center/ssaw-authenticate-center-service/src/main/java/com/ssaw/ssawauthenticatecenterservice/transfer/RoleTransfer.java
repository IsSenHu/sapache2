package com.ssaw.ssawauthenticatecenterservice.transfer;

import com.ssaw.ssawauthenticatecenterfeign.vo.role.RoleVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.role.RoleEntity;
import org.springframework.stereotype.Component;

/**
 * @author HuSen.
 * @date 2018/12/14 19:48.
 */
@Component
public class RoleTransfer {

    public RoleEntity dto2Entity(RoleVO dto) {
        RoleEntity entity = null;
        if(null != dto) {
            entity = new RoleEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
        }
        return entity;
    }

    public RoleVO entity2Dto(RoleEntity entity) {
        RoleVO dto = null;
        if(null != entity) {
            dto = new RoleVO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setCreateMan(entity.getCreateMan());
            dto.setCreateTime(entity.getCreateTime());
            dto.setModifyMan(entity.getModifyMan());
            dto.setModifyTime(entity.getModifyTime());
        }
        return dto;
    }
}
