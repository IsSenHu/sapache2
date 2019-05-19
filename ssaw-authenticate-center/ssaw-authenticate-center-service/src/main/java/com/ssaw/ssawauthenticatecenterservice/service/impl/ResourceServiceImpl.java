package com.ssaw.ssawauthenticatecenterservice.service.impl;

import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.event.local.UploadScopeAndWhiteListFinishedEvent;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawauthenticatecenterfeign.vo.UploadVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.resource.*;
import com.ssaw.ssawauthenticatecenterfeign.vo.TreeVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.resource.ResourceEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.scope.ScopeEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.resource.ResourceRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.scope.ScopeRepository;
import com.ssaw.ssawauthenticatecenterservice.service.MenuService;
import com.ssaw.ssawauthenticatecenterservice.service.ResourceService;
import com.ssaw.ssawauthenticatecenterservice.service.ScopeService;
import com.ssaw.ssawauthenticatecenterservice.specification.ResourceSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static com.ssaw.commons.constant.Constants.ResultCodes.*;

/**
 * @author HuSen.
 * @date 2018/12/12 13:59.
 */
@Slf4j
@Service
public class ResourceServiceImpl extends BaseService implements ResourceService {

    private final ApplicationContext applicationContext;
    private final ResourceRepository resourceRepository;
    private final ScopeRepository scopeRepository;
    private final MenuService menuService;
    private final ScopeService scopeService;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository, ScopeRepository scopeRepository, MenuService menuService, ScopeService scopeService, ApplicationContext applicationContext) {
        this.resourceRepository = resourceRepository;
        this.scopeRepository = scopeRepository;
        this.menuService = menuService;
        this.scopeService = scopeService;
        this.applicationContext = applicationContext;
    }

    /**
     * 新增资源服务
     * @param createResourceVO 新增资源服务请求对象
     * @return 新增结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<CreateResourceVO> add(CreateResourceVO createResourceVO) {
        ResourceEntity entity = CopyUtil.copyProperties(createResourceVO, new ResourceEntity());
        if(Objects.isNull(entity)) {
            return CommonResult.createResult(PARAM_ERROR, "参数为空!", null);
        }
        if(resourceRepository.countByResourceId(entity.getResourceId()) > 0) {
            return CommonResult.createResult(DATA_EXIST, "该资源ID已存在!", createResourceVO);
        }
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateMan(UserContextHolder.currentUser().getUsername());
        resourceRepository.save(entity);
        return CommonResult.createResult(SUCCESS, "成功!", createResourceVO);
    }

    /**
     * 分页查询资源服务
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    @Override
    public TableData<ResourceVO> page(PageReqVO<QueryResourceVO> pageReqVO) {
        Pageable pageable = getPageRequest(pageReqVO);
        Page<ResourceEntity> page = resourceRepository.findAll(new ResourceSpecification(pageReqVO.getData()), pageable);
        TableData<ResourceVO> tableData = new TableData<>();
        setTableData(page, tableData);
        tableData.setContent(page.getContent().stream().map(input -> CopyUtil.copyProperties(input, new ResourceVO())).collect(Collectors.toList()));
        return tableData;
    }

    /**
     * 根据ID查询资源服务
     * @param id ID
     * @return 资源服务
     */
    @Override
    public CommonResult<ResourceVO> findById(Long id) {
        if(Objects.isNull(id)) {
            return CommonResult.createResult(PARAM_ERROR, "资源ID为空!", null);
        }
        return resourceRepository.findById(id)
                .map(resourceEntity -> CommonResult.createResult(SUCCESS, "成功!", CopyUtil.copyProperties(resourceEntity, new ResourceVO())))
                .orElseGet(() -> CommonResult.createResult(DATA_NOT_EXIST, "该资源不存在!", null));
    }

    /**
     * 修改资源服务
     * @param updateResourceVO 修改资源请求对象
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<UpdateResourceVO> update(UpdateResourceVO updateResourceVO) {
        return resourceRepository.findById(updateResourceVO.getId()).map(entity -> {
            if(!StringUtils.equals(entity.getResourceId(), updateResourceVO.getResourceId())
                    && resourceRepository.countByResourceId(updateResourceVO.getResourceId()) > 0) {
                return CommonResult.createResult(PARAM_ERROR, "该资源ID已存在!", updateResourceVO);
            }
            entity.setResourceId(updateResourceVO.getResourceId());
            entity.setDescription(updateResourceVO.getDescription());
            entity.setModifyTime(LocalDateTime.now());
            entity.setModifyMan(UserContextHolder.currentUser().getUsername());
            resourceRepository.save(entity);
            return CommonResult.createResult(SUCCESS, "成功!", updateResourceVO);
        }).orElseGet(() -> CommonResult.createResult(DATA_NOT_EXIST, "该资源不存在!", updateResourceVO));
    }

    /**
     * 根据ID删除资源服务
     * @param id ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<Long> delete(Long id) {
        if(Objects.isNull(id)) {
            return CommonResult.createResult(PARAM_ERROR, "资源ID为空!", null);
        }
        resourceRepository.deleteById(id);
        return CommonResult.createResult(SUCCESS, "成功!", id);
    }

    /**
     * 根据资源ID查询资源
     * @param resourceId 资源ID
     * @return 资源服务
     */
    @Override
    public CommonResult<List<ResourceVO>> search(String resourceId) {
        Pageable pageable = PageRequest.of(0, 20);
        QueryResourceVO queryResourceVO = new QueryResourceVO();
        queryResourceVO.setResourceId(StringUtils.equals("none", resourceId) ? "" : resourceId);
        Page<ResourceEntity> page = resourceRepository.findAll(new ResourceSpecification(queryResourceVO), pageable);
        if(CollectionUtils.isNotEmpty(page.getContent())) {
            List<ResourceVO> resourceVOList = page.getContent().stream().map(input -> CopyUtil.copyProperties(input, new ResourceVO())).collect(Collectors.toList());
            return CommonResult.createResult(SUCCESS, "成功!", resourceVOList);
        } else {
            return CommonResult.createResult(SUCCESS, "成功!", new ArrayList<>(0));
        }
    }

    /**
     * 查询所有的资源服务
     * @return 所有的资源服务
     */
    @Override
    public CommonResult<List<ResourceVO>> findAll() {
        return CommonResult.createResult(SUCCESS, "成功!",
                resourceRepository.findAll().stream().map(input -> CopyUtil.copyProperties(input, new ResourceVO())).collect(Collectors.toList()));
    }

    /**
     * 根据资源ID查询出树结构作用域
     * @param ids 资源IDS
     * @return 树结构作用域
     */
    @Override
    public CommonResult<EditClientScopeVO> findAllScopeByResourceIds(String ids) {
        EditClientScopeVO clientScopeDto = new EditClientScopeVO();
        if(StringUtils.isNotEmpty(ids)) {
            List<ResourceEntity> allById = resourceRepository.findAllById(Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList()));
            List<TreeVO> treeVOList = new ArrayList<>(allById.size());
            for(ResourceEntity entity : allById) {
                TreeVO treeVO = new TreeVO();
                treeVO.setId(entity.getId());
                treeVO.setLabel(entity.getResourceId());
                List<ScopeEntity> allByResourceId = scopeRepository.findAllByResourceId(entity.getId());
                List<TreeVO> children = allByResourceId.stream().map(x -> {
                    TreeVO child = new TreeVO();
                    child.setId(x.getId());
                    child.setLabel(x.getScope());
                    return child;
                }).collect(Collectors.toList());
                treeVO.setChildren(children);
                treeVOList.add(treeVO);
            }
            clientScopeDto.setTreeVOS(treeVOList);
        } else {
            clientScopeDto.setTreeVOS(new ArrayList<>(0));
            clientScopeDto.setDefaultExpandedKeys(new ArrayList<>(0));
        }
        return CommonResult.createResult(SUCCESS, "成功!", clientScopeDto);
    }

    /**
     * 上传资源服务
     * @param uploadResourceVO 上传资源服务请求对象
     * @return 上传结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<UploadResourceVO> uploadResource(UploadResourceVO uploadResourceVO) {
        ResourceEntity byResourceId = resourceRepository.findByResourceId(uploadResourceVO.getResourceId());
        ResourceEntity resourceEntity;
        if (Objects.isNull(byResourceId)) {
            ResourceEntity entity = CopyUtil.copyProperties(uploadResourceVO, new ResourceEntity());
            entity.setCreateTime(LocalDateTime.now());
            entity.setCreateMan("ssaw");
            resourceEntity = resourceRepository.save(entity);
        } else {
            byResourceId.setResourceId(uploadResourceVO.getResourceId());
            byResourceId.setDescription(uploadResourceVO.getDescription());
            byResourceId.setModifyTime(LocalDateTime.now());
            resourceEntity = resourceRepository.save(byResourceId);
        }
        uploadResourceVO.setId(resourceEntity.getId());
        return CommonResult.createResult(SUCCESS, "成功", uploadResourceVO);
    }

    /**
     * 上传资源, 作用域, 白名单, 按钮, 菜单
     * @param uploadVO 认证信息
     * @return 上传结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<UploadVO> uploadAuthenticateInfo(UploadVO uploadVO) {
        // 首先上传资源服务
        CommonResult<UploadResourceVO> uploadResourceResult = uploadResource(uploadVO.getUploadResourceVO());
        Assert.state(uploadResourceResult.getCode() == SUCCESS, "上传资源服务失败");
        // 然后上传作用域
        uploadVO.getScopeVOList().forEach(x -> x.setResourceId(uploadResourceResult.getData().getId()));
        CommonResult<String> uploadScopesResult = scopeService.uploadScopes(uploadResourceResult.getData().getId(), uploadVO.getScopeVOList());
        Assert.state(uploadScopesResult.getCode() == SUCCESS, "上传作用域失败");
        // 其次上传白名单
        CommonResult<String> uploadWhiteListResult = menuService.uploadWhiteList(uploadVO.getWhiteList(), uploadResourceResult.getData().getResourceId());
        Assert.state(uploadWhiteListResult.getCode() == SUCCESS, "上传白名单失败");
        // 再上传菜单
        CommonResult<String> uploadMenusResult = menuService.uploadMenus(uploadVO.getMenuVO(), uploadResourceResult.getData().getResourceId());
        Assert.state(uploadMenusResult.getCode() == SUCCESS, "上传菜单失败");
        // 最后上传按钮
        CommonResult<String> uploadButtonsResult = menuService.uploadButtons(uploadVO.getButtonVOS(), uploadResourceResult.getData().getResourceId());
        Assert.state(uploadButtonsResult.getCode() == SUCCESS, "上传按钮失败");
        // 发布上传作用域与白名单完成事件 重新刷新作用域
        applicationContext.publishEvent(new UploadScopeAndWhiteListFinishedEvent(uploadResourceResult.getData().getResourceId()));
        return CommonResult.createResult(SUCCESS, "上传成功", uploadVO);
    }
}
