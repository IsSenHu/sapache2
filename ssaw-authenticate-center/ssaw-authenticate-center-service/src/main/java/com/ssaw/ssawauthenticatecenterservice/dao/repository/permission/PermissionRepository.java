package com.ssaw.ssawauthenticatecenterservice.dao.repository.permission;

import com.ssaw.ssawauthenticatecenterservice.dao.entity.permission.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/13 17:06.
 */
@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long>, JpaSpecificationExecutor<PermissionEntity> {

    /**
     * 根据名称计数
     * @param name 名称
     * @return 数量
     */
    long countByName(String name);

    /**
     * 根据资源ID查询所有权限
     * @param resourceId 资源ID
     * @return 权限集合
     */
    List<PermissionEntity> findAllByResourceId(Long resourceId);

    /**
     * 删除不包括的权限
     * @param scopeIds 作用域ID集合
     * @param resourceId 资源主键
     */
    void deleteAllByResourceIdAndScopeIdNotIn(Long resourceId, List<Long> scopeIds);
}
