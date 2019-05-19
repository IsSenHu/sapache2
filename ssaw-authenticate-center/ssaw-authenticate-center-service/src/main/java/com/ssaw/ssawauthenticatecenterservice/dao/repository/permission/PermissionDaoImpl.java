package com.ssaw.ssawauthenticatecenterservice.dao.repository.permission;

import com.ssaw.ssawauthenticatecenterservice.dao.entity.permission.PermissionEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;

/**
 * @author HuSen.
 * @date 2018/12/13 17:06.
 */
public class PermissionDaoImpl extends SimpleJpaRepository<PermissionEntity, Long> implements PermissionDao {
    public PermissionDaoImpl(@Autowired EntityManager entityManager) {
        super(PermissionEntity.class, entityManager);
        em = entityManager;
    }

    private final EntityManager em;
}
