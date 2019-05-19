package com.ssaw.ssawauthenticatecenterservice.dao.repository.scope;

import com.ssaw.ssawauthenticatecenterservice.dao.entity.scope.ScopeEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.ScopeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;

/**
 * @author HuSen.
 * @date 2018/12/12 11:14.
 */
public class ScopeDaoImpl extends SimpleJpaRepository<ScopeEntity, Long> implements ScopeDao {

    public ScopeDaoImpl(@Autowired EntityManager entityManager) {
        super(ScopeEntity.class, entityManager);
        em = entityManager;
    }

    private final EntityManager em;


}
