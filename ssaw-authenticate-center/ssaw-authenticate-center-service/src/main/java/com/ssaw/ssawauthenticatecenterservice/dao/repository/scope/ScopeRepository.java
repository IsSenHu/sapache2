package com.ssaw.ssawauthenticatecenterservice.dao.repository.scope;

import com.ssaw.ssawauthenticatecenterservice.dao.entity.scope.ScopeEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.ScopeDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/12 10:35.
 */
@Repository
public interface ScopeRepository extends JpaRepository<ScopeEntity, Long>, JpaSpecificationExecutor<ScopeEntity>, ScopeDao {

    /**
     * 根据scope或uri计数
     * @param scope scope
     * @param uri uri
     * @return 数量
     */
    long countByScopeOrUri(String scope, String uri);

    /**
     * 根据scope计数
     * @param scope scope
     * @return 数量
     */
    long countByScope(String scope);

    /**
     * 根据uri计数
     * @param uri uri
     * @return 数量
     */
    long countByUri(String uri);

    /**
     * 根据scope分页查询permissionId为空的作用域
     * @param scope scope
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<ScopeEntity> findAllByScopeLikeAndPermissionIdIsNull (String scope, Pageable pageable);

    /**
     * 根据scope分页查询作用域
     * @param scope scope
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<ScopeEntity> findAllByScopeLike (String scope, Pageable pageable);

    /**
     * 分页查询permissionId为空的作用域
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<ScopeEntity> findAllByPermissionIdIsNull(Pageable pageable);

    /**
     * 根据资源ID查询作用域
     * @param resourceId 资源ID
     * @return 作用域
     */
    List<ScopeEntity> findAllByResourceId(Long resourceId);

    /**
     * 删除不再指定scopes里面的作用域
     * @param resourceId 资源主键
     * @param scopes scopes
     */
    void deleteAllByResourceIdAndScopeNotIn(Long resourceId, List<String> scopes);

    /**
     * 根据Scopes查询作用域
     * @param resourceId 资源主键
     * @param scopes scopes
     * @return 作用域
     */
    List<ScopeEntity> findAllByResourceIdAndScopeIn(Long resourceId, List<String> scopes);
}
