package com.ssaw.ssawauthenticatecenterservice.dao.repository.role;

import com.ssaw.ssawauthenticatecenterservice.dao.entity.role.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author HuSen.
 * @date 2018/12/14 17:51.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {

    /**
     * 根据名称计数
     * @param name 名称
     * @return 数量
     */
    long countByName(String name);

    /**
     * 根据名称分页查询
     * @param name 名称
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<RoleEntity> findAllByNameLike(String name, Pageable pageable);
}
