package com.ssaw.ssawauthenticatecenterservice.dao.repository.user;

import com.ssaw.ssawauthenticatecenterservice.dao.entity.user.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hszyp
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    /**
     * 根据用户ID查询用户角色关系
     * @param userId 用户ID
     * @return 用户角色关系
     */
    UserRoleEntity findByUserId(Long userId);
}
