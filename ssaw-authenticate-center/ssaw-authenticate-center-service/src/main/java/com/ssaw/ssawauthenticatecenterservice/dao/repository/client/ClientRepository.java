package com.ssaw.ssawauthenticatecenterservice.dao.repository.client;

import com.ssaw.ssawauthenticatecenterservice.dao.entity.client.ClientDetailsEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.ClientDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author HuSen.
 * @date 2018/12/11 9:34.
 */
@Repository
public interface ClientRepository extends JpaRepository<ClientDetailsEntity, String>, JpaSpecificationExecutor<ClientDetailsEntity>, ClientDao {
    /**
     * 根据clientId计数
     * @param clientId clientId
     * @return 数量
     */
    long countByClientId(String clientId);
}
