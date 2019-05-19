package com.ssaw.ssawauthenticatecenterservice.service.impl;

import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawauthenticatecenterfeign.vo.role.*;
import com.ssaw.ssawauthenticatecenterfeign.vo.TreeVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.permission.PermissionEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.resource.ResourceEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.role.RoleEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.role.RolePermissionEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.permission.PermissionRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.resource.ResourceRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.role.RoleRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.role.permission.RolePermissionRepository;
import com.ssaw.ssawauthenticatecenterservice.service.RoleService;
import com.ssaw.ssawauthenticatecenterservice.specification.RoleSpecification;
import com.ssaw.ssawauthenticatecenterservice.transfer.RoleTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.ssaw.commons.constant.Constants.ResultCodes.DATA_EXIST;
import static com.ssaw.commons.constant.Constants.ResultCodes.DATA_NOT_EXIST;
import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;

/**
 * @author HuSen.
 * @date 2018/12/14 17:53.
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseService implements RoleService {

    private final RoleTransfer roleTransfer;

    private final RoleRepository roleRepository;

    private final RolePermissionRepository rolePermissionRepository;

    private final ResourceRepository resourceRepository;

    private final PermissionRepository permissionRepository;

    @Autowired
    public RoleServiceImpl(RoleTransfer roleTransfer, RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository, PermissionRepository permissionRepository, ResourceRepository resourceRepository) {
        this.roleTransfer = roleTransfer;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.resourceRepository = resourceRepository;
    }

    /**
     * 新增角色
     * @param createRoleVO 新增角色请求对象
     * @return 新增结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<CreateRoleVO> add(CreateRoleVO createRoleVO) {
        if(roleRepository.countByName(createRoleVO.getName()) > 0) {
            return CommonResult.createResult(DATA_EXIST, "该权限名称已存在!", createRoleVO);
        }
        RoleEntity entity = CopyUtil.copyProperties(createRoleVO, new RoleEntity());
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateMan(UserContextHolder.currentUser().getUsername());
        roleRepository.save(entity);
        return CommonResult.createResult(SUCCESS, "成功!", createRoleVO);
    }

    /**
     * 根据ID查询角色
     * @param id ID
     * @return 角色
     */
    @Override
    public CommonResult<EditRoleVO> findById(Long id) {
        RoleVO roleVO = roleRepository.findById(id).map(roleTransfer::entity2Dto).orElse(null);
        if(roleVO == null) {
            return CommonResult.createResult(DATA_NOT_EXIST, "该角色不存在!", null);
        }
        EditRoleVO editRoleVO = new EditRoleVO();
        editRoleVO.setRoleVO(roleVO);

        // 查询出所有的资源
        List<ResourceEntity> resourceRepositoryAll = resourceRepository.findAll();
        List<TreeVO> treeVOS = new ArrayList<>(resourceRepositoryAll.size());
        if(CollectionUtils.isNotEmpty(resourceRepositoryAll)) {
            for(ResourceEntity resourceEntity : resourceRepositoryAll) {
                TreeVO resource = new TreeVO();
                resource.setId(resourceEntity.getId());
                resource.setLabel(resourceEntity.getResourceId());
                // 获取该资源的所有权限
                List<PermissionEntity> permissionRepositoryAllByResourceId = permissionRepository.findAllByResourceId(resourceEntity.getId());
                List<TreeVO> children = new ArrayList<>(permissionRepositoryAllByResourceId.size());
                if(CollectionUtils.isNotEmpty(permissionRepositoryAllByResourceId)) {
                    for(PermissionEntity permissionEntity : permissionRepositoryAllByResourceId) {
                        TreeVO treeVO = new TreeVO();
                        treeVO.setId(Long.valueOf(permissionEntity.getResourceId().toString() + permissionEntity.getId().toString()));
                        treeVO.setLabel(permissionEntity.getName());
                        children.add(treeVO);
                    }
                }
                resource.setChildren(children);
                treeVOS.add(resource);
            }
        }
        // 设置树数据
        editRoleVO.setTreeVOS(treeVOS);
        // 设置用户已拥有的权限为选中
        List<Long> defaultCheckedKeys = new ArrayList<>();
        List<RolePermissionEntity> allByRoleId = rolePermissionRepository.findAllByRoleId(id);
        if(CollectionUtils.isNotEmpty(allByRoleId)) {
            Set<Long> collectPermissionId = allByRoleId.stream().map(RolePermissionEntity::getPermissionId).collect(Collectors.toSet());
            List<PermissionEntity> allById = permissionRepository.findAllById(collectPermissionId);
            defaultCheckedKeys = allById.stream().map(x -> Long.valueOf(x.getResourceId().toString() + x.getId().toString())).collect(Collectors.toList());
        }
        editRoleVO.setDefaultCheckedKeys(defaultCheckedKeys);
        return CommonResult.createResult(SUCCESS, "成功!", editRoleVO);
    }

    /**
     * 分页查询角色
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    @Override
    public TableData<RoleVO> page(PageReqVO<QueryRoleVO> pageReqVO) {
        Pageable pageable = getPageRequest(pageReqVO);
        Page<RoleEntity> page = roleRepository.findAll(new RoleSpecification(pageReqVO.getData()), pageable);
        TableData<RoleVO> tableData = new TableData<>();
        setTableData(page, tableData);
        tableData.setContent(page.getContent().stream().map(roleTransfer::entity2Dto).collect(Collectors.toList()));
        return tableData;
    }

    /**
     * 修改角色
     * @param updateRoleVO 修改角色请求对象
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<UpdateRoleVO> update(UpdateRoleVO updateRoleVO) {
        return roleRepository.findById(updateRoleVO.getId())
            .map(entity -> {
                if(!StringUtils.equals(entity.getName(), updateRoleVO.getName()) && roleRepository.countByName(updateRoleVO.getName()) > 0) {
                    return CommonResult.createResult(DATA_EXIST, "该角色名称已存在!", updateRoleVO);
                }
                entity.setName(updateRoleVO.getName());
                entity.setDescription(updateRoleVO.getDescription());
                entity.setModifyTime(LocalDateTime.now());
                List<TreeVO> permissions = updateRoleVO.getPermissions();
                // 删除现有的角色权限关系
                rolePermissionRepository.deleteAllByRoleId(updateRoleVO.getId());
                // 重新新增角色权限关系
                List<RolePermissionEntity> rolePermissionEntities = new ArrayList<>();
                for(TreeVO treeVO : permissions) {
                    List<TreeVO> children = treeVO.getChildren();
                    if(CollectionUtils.isNotEmpty(children)) {
                        List<RolePermissionEntity> collect = children.stream().map(x -> {
                            String realId = StringUtils.substring(x.getId().toString(), treeVO.getId().toString().length());
                            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
                            rolePermissionEntity.setRoleId(updateRoleVO.getId());
                            rolePermissionEntity.setPermissionId(Long.valueOf(realId));
                            rolePermissionEntity.setCreateTime(LocalDateTime.now());
                            rolePermissionEntity.setModifyTime(LocalDateTime.now());
                            return rolePermissionEntity;
                        }).collect(Collectors.toList());
                        rolePermissionEntities.addAll(collect);
                    }
                }
                rolePermissionRepository.saveAll(rolePermissionEntities);
                entity.setModifyMan(UserContextHolder.currentUser().getUsername());
                roleRepository.save(entity);
                return CommonResult.createResult(SUCCESS, "成功!", updateRoleVO);
            })
            .orElseGet(() -> CommonResult.createResult(DATA_NOT_EXIST, "该角色不存在!", updateRoleVO));
    }

    /**
     * 根据ID删除角色
     * @param id ID
     * @return 删除结果
     */
    @Override
    public CommonResult<Long> delete(Long id) {
        roleRepository.deleteById(id);
        return CommonResult.createResult(SUCCESS, "成功!", id);
    }

    /**
     * 根据角色名搜索角色
     * @param role 角色名称
     * @return 角色数据
     */
    @Override
    public CommonResult<List<RoleVO>> search(String role) {
        Pageable pageable = PageRequest.of(0, 20);
        Page<RoleEntity> page;
        final String none = "none";
        if(none.equals(role)) {
            page = roleRepository.findAll(pageable);
        } else {
            page = roleRepository.findAllByNameLike("%".concat(role).concat("%"), pageable);
        }
        return CommonResult.createResult(SUCCESS, "成功!",
                page.getContent().stream().map(roleTransfer::entity2Dto).collect(Collectors.toList()));
    }
}
