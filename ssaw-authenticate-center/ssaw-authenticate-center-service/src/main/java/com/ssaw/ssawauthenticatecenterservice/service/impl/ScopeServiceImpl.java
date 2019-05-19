package com.ssaw.ssawauthenticatecenterservice.service.impl;

import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.QueryScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.CreateScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.UpdateScopeVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.permission.PermissionEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.resource.ResourceEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.scope.ScopeEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.permission.PermissionRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.resource.ResourceRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.scope.ScopeRepository;
import com.ssaw.ssawauthenticatecenterservice.service.ScopeService;
import com.ssaw.ssawauthenticatecenterservice.specification.ScopeSpecification;
import com.ssaw.ssawauthenticatecenterservice.transfer.ScopeTransfer;
import com.ssaw.ssawauthenticatecenterservice.authentication.cache.CacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import static com.ssaw.commons.constant.Constants.ResultCodes.*;

/**
 * @author HuSen.
 * @date 2018/12/12 11:38.
 */
@Slf4j
@Service
public class ScopeServiceImpl extends BaseService implements ScopeService {

    private final ScopeTransfer scopeTransfer;
    private final ScopeRepository scopeRepository;
    private final PermissionRepository permissionRepository;
    private final ResourceRepository resourceRepository;

    @Autowired
    public ScopeServiceImpl(ScopeRepository scopeRepository, ScopeTransfer scopeTransfer, PermissionRepository permissionRepository, ResourceRepository resourceRepository) {
        this.scopeRepository = scopeRepository;
        this.scopeTransfer = scopeTransfer;
        this.permissionRepository = permissionRepository;
        this.resourceRepository = resourceRepository;
    }

    /**
     * 新增作用域
     * @param createScopeVO 新增作用域请求对象
     * @return 新增结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<CreateScopeVO> add(CreateScopeVO createScopeVO) {
        ScopeEntity entity = CopyUtil.copyProperties(createScopeVO, new ScopeEntity());
        if(null == entity) {
            return CommonResult.createResult(PARAM_ERROR, "参数为空!", null);
        }
        if(scopeRepository.countByScopeOrUri(createScopeVO.getScope(), createScopeVO.getUri()) > 0) {
            return CommonResult.createResult(DATA_EXIST, "该scope或uri已存在!", createScopeVO);
        }
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateMan(UserContextHolder.currentUser().getUsername());
        scopeRepository.save(entity);
        return CommonResult.createResult(SUCCESS, "成功!", createScopeVO);
    }

    /**
     * 分页查询作用域
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    @Override
    public TableData<ScopeVO> page(PageReqVO<QueryScopeVO> pageReqVO) {
        Pageable pageable = getPageRequest(pageReqVO);
        Page<ScopeEntity> page = scopeRepository.findAll(new ScopeSpecification(pageReqVO.getData()), pageable);
        TableData<ScopeVO> tableData = new TableData<>();
        setTableData(page, tableData);
        tableData.setContent(page.getContent().stream().map(scopeTransfer::entity2Dto).collect(Collectors.toList()));
        return tableData;
    }

    /**
     * 根据ID删除作用域
     * @param id ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<Long> delete(Long id) {
        if(Objects.isNull(id)) {
            return CommonResult.createResult(PARAM_ERROR, "作用域ID为空!", null);
        }
        scopeRepository.deleteById(id);
        return CommonResult.createResult(SUCCESS, "成功!", id);
    }

    /**
     * 根据ID查询作用域
     * @param id ID
     * @return 作用域
     */
    @Override
    public CommonResult<ScopeVO> findById(Long id) {
        if(Objects.isNull(id)) {
            return CommonResult.createResult(PARAM_ERROR, "作用域ID为空!", null);
        }
        return scopeRepository.findById(id)
                .map(scopeEntity -> CommonResult.createResult(SUCCESS, "成功!", scopeTransfer.entity2Dto(scopeEntity)))
                .orElseGet(() -> CommonResult.createResult(DATA_NOT_EXIST, "该作用域不存在!", null));
    }

    /**
     * 修改作用域
     * @param updateScopeVO 修改作用域请求对象
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<UpdateScopeVO> update(UpdateScopeVO updateScopeVO) {
        return scopeRepository.findById(updateScopeVO.getId())
                .map(entity -> {
                    if(!StringUtils.equals(updateScopeVO.getScope(), entity.getScope()) && scopeRepository.countByScope(updateScopeVO.getScope()) > 1) {
                        return CommonResult.createResult(DATA_EXIST, "该Scope已存在!", updateScopeVO);
                    }
                    if(!StringUtils.equals(updateScopeVO.getUri(), entity.getUri()) && scopeRepository.countByUri(updateScopeVO.getUri()) > 1) {
                        return CommonResult.createResult(DATA_EXIST, "该Uri已存在!", updateScopeVO);
                    }
                    entity.setScope(updateScopeVO.getScope());
                    entity.setUri(updateScopeVO.getUri());
                    entity.setResourceId(updateScopeVO.getResourceId());
                    entity.setModifyTime(LocalDateTime.now());
                    entity.setModifyMan(UserContextHolder.currentUser().getUsername());
                    scopeRepository.save(entity);
                    return CommonResult.createResult(SUCCESS, "成功!", updateScopeVO);
                }).orElseGet(() -> CommonResult.createResult(DATA_NOT_EXIST, "该作用域不存在!", updateScopeVO));
    }

    /**
     * 根据作用域名称搜索作用域
     * @param scope 作用域名称
     * @return 作用域
     */
    @Override
    public CommonResult<List<ScopeVO>> search(String scope) {
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<ScopeEntity> page;
        final String none = "none";
        if(StringUtils.equals(none, scope)) {
            page = scopeRepository.findAllByPermissionIdIsNull(pageRequest);
        } else {
            page = scopeRepository.findAllByScopeLikeAndPermissionIdIsNull("%".concat(scope.trim()).concat("%"), pageRequest);
        }
        return CommonResult.createResult(SUCCESS, "成功!",
                page.getContent().stream().map(scopeTransfer::entity2DtoNotGetResourceName).collect(Collectors.toList()));
    }

    /**
     * 根据作用域名称搜索作用域
     * @param scope 作用域名称
     * @return 作用域
     */
    @Override
    public CommonResult<List<ScopeVO>> searchForUpdate(String scope) {
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<ScopeEntity> page;
        final String none = "none";
        if(StringUtils.equals(none, scope)) {
            page = scopeRepository.findAll(pageRequest);
        } else {
            page = scopeRepository.findAllByScopeLike("%".concat(scope.trim()).concat("%"), pageRequest);
        }
        return CommonResult.createResult(SUCCESS, "成功!",
                page.getContent().stream().map(scopeTransfer::entity2DtoNotGetResourceName).collect(Collectors.toList()));
    }

    /**
     * 上传作用域
     * @param resourceId 资源主键
     * @param scopeVOList 作用域集合
     * @return 上传结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<String> uploadScopes(Long resourceId, List<ScopeVO> scopeVOList) {
        List<String> scopes = scopeVOList.stream().map(ScopeVO::getScope).collect(Collectors.toList());

        // 先删除不要的作用域
        scopeRepository.deleteAllByResourceIdAndScopeNotIn(resourceId, scopes);
        // 得到还存在的权限
        List<ScopeEntity> allByScopeIn = scopeRepository.findAllByResourceIdAndScopeIn(resourceId, scopes);
        List<Long> allScopeId = allByScopeIn.stream().map(ScopeEntity::getId).collect(Collectors.toList());
        // 再删除不要的权限
        permissionRepository.deleteAllByResourceIdAndScopeIdNotIn(resourceId, allScopeId);
        Set<String> scopeSet = allByScopeIn.stream().map(ScopeEntity::getScope).collect(Collectors.toSet());
        Map<String, ScopeVO> updateScopeMap = new HashMap<>(scopeVOList.size());
        List<ScopeVO> newScopeList = new ArrayList<>();
        for (ScopeVO scopeVO : scopeVOList) {
            if (scopeSet.contains(scopeVO.getScope())) {
                updateScopeMap.put(scopeVO.getScope(), scopeVO);
            } else {
                newScopeList.add(scopeVO);
            }
        }

        allByScopeIn.forEach(entity -> {
            ScopeVO scopeVO = updateScopeMap.get(entity.getScope());
            entity.setUri(scopeVO.getUri());
            entity.setScope(scopeVO.getScope());
            entity.setResourceId(scopeVO.getResourceId());
            Optional<PermissionEntity> byId = permissionRepository.findById(entity.getPermissionId());
            byId.ifPresent(permissionEntity -> {
                permissionEntity.setScopeId(entity.getId());
                permissionEntity.setResourceId(entity.getResourceId());
                permissionEntity.setName(entity.getScope());
                permissionEntity.setDescription(entity.getUri());
                permissionEntity.setModifyTime(LocalDateTime.now());
                permissionRepository.save(permissionEntity);
                entity.setPermissionId(permissionEntity.getId());
            });
            entity.setModifyTime(LocalDateTime.now());
            scopeRepository.save(entity);
        });

        List<ScopeEntity> collect = newScopeList.stream().map(scopeTransfer::dto2Entity).collect(Collectors.toList());
        collect.forEach(entity -> {
            scopeRepository.save(entity);
            entity.setCreateTime(LocalDateTime.now());
            PermissionEntity permissionEntity = new PermissionEntity();
            permissionEntity.setScopeId(entity.getId());
            permissionEntity.setResourceId(entity.getResourceId());
            permissionEntity.setName(entity.getScope());
            permissionEntity.setDescription(entity.getUri());
            permissionEntity.setCreateTime(LocalDateTime.now());
            permissionRepository.save(permissionEntity);
            entity.setPermissionId(permissionEntity.getId());
            entity.setCreateTime(LocalDateTime.now());
            scopeRepository.save(entity);
        });
        return CommonResult.createResult(SUCCESS, "成功", UUID.randomUUID().toString());
    }

    /**
     * 刷新作用域缓存
     * @param source 来源
     */
    @Override
    public void refreshScope(String source) {
        log.info("因为资源服务:{} 上传作用域, 所以开始刷新作用域", source);
        ResourceEntity resourceEntity = resourceRepository.findByResourceId(source);
        Assert.notNull(resourceEntity, "找不到资源服务");
        List<ScopeEntity> scopeEntities = scopeRepository.findAllByResourceId(resourceEntity.getId());
        CacheManager.refreshScopes(source, scopeEntities.stream().map(scopeTransfer::entity2Dto).collect(Collectors.toList()));
    }
}
