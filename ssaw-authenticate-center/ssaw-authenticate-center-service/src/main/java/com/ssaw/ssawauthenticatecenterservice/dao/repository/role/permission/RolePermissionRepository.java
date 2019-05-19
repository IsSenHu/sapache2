package com.ssaw.ssawauthenticatecenterservice.dao.repository.role.permission;

import com.ssaw.ssawauthenticatecenterservice.dao.entity.role.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/14 20:15.
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Long>, JpaSpecificationExecutor<RolePermissionEntity> {

    /**
     * 根据角色ID查询所有的角色权限关系
     * @param roleId 角色ID
     * @return 角色权限关系
     */
    List<RolePermissionEntity> findAllByRoleId(Long roleId);

    /**
     * 根据角色ID删除所有角色权限关系
     * @param roleId 角色ID
     */
    void deleteAllByRoleId(Long roleId);
}
