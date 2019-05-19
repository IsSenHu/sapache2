package com.ssaw.ssawauthenticatecenterservice.dao.repository.resource;

import com.ssaw.ssawauthenticatecenterservice.dao.entity.resource.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author HuSen.
 * @date 2018/12/12 10:34.
 */
@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long>, JpaSpecificationExecutor<ResourceEntity> {

    /**
     * 根据资源ID计数
     * @param resourceId 资源ID
     * @return 数量
     */
    long countByResourceId(String resourceId);

    /**
     * 根据资源ID删除
     * @param resourceId 资源ID
     */
    void deleteByResourceId(String resourceId);

    /**
     * 根据资源ID查询资源
     * @param resourceId 资源ID
     * @return 资源
     */
    ResourceEntity findByResourceId(String resourceId);
}
