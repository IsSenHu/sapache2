package com.ssaw.ssawauthenticatecenterservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.ssaw.commons.util.app.ApplicationContextUtil;
import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawauthenticatecenterfeign.vo.permission.PermissionVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.*;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.permission.PermissionEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.resource.ResourceEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.role.RoleEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.role.RolePermissionEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.scope.ScopeEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.user.UserEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.user.UserRoleEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.permission.PermissionRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.resource.ResourceRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.role.RoleRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.role.permission.RolePermissionRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.scope.ScopeRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.user.UserRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.user.UserRoleRepository;
import com.ssaw.ssawauthenticatecenterservice.service.MenuService;
import com.ssaw.ssawauthenticatecenterservice.specification.UserSpecification;
import com.ssaw.ssawauthenticatecenterservice.transfer.PermissionTransfer;
import com.ssaw.ssawauthenticatecenterservice.service.UserService;
import com.ssaw.ssawauthenticatecenterservice.details.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ssaw.commons.constant.Constants.ResultCodes.*;
import static com.ssaw.commons.vo.CommonResult.createResult;

/**
 * @author HuSen.
 * @date 2018/12/11 9:58.
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseService implements UserService {

    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final PermissionTransfer permissionTransfer;
    private final UserRepository userRepository;
    private final MenuService menuService;
    private final ScopeRepository scopeRepository;
    private final ResourceRepository resourceRepository;

    @Autowired
    public UserServiceImpl(UserRoleRepository userRoleRepository, RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository, PermissionRepository permissionRepository, PermissionTransfer permissionTransfer, UserRepository userRepository, MenuService menuService, ScopeRepository scopeRepository, ResourceRepository resourceRepository) {
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.permissionTransfer = permissionTransfer;
        this.userRepository = userRepository;
        this.menuService = menuService;
        this.scopeRepository = scopeRepository;
        this.resourceRepository = resourceRepository;
    }

    /**
     * 根据ID查询用户
     * @param userId 用户ID
     * @return 用户
     */
    @Override
    public CommonResult<UserVO> findById(Long userId) {
        if (null == userId) {
            return CommonResult.createResult(PARAM_ERROR, "用户ID必传!", null);
        }
        Optional<UserEntity> byId = userRepository.findById(userId);
        return byId
                .map(userEntity -> CommonResult.createResult(SUCCESS, "成功!", CopyUtil.copyProperties(userEntity, new UserVO())))
                .orElseGet(() -> CommonResult.createResult(ERROR, "该用户不存在!", null));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity byUsername = userRepository.findByUsername(username);
        if(Objects.isNull(byUsername)) {
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl();
        userDetailsImpl.setId(byUsername.getId());
        userDetailsImpl.setUsername(byUsername.getUsername());
        userDetailsImpl.setPassword(byUsername.getPassword());
        userDetailsImpl.setInner(byUsername.getInner());
        userDetailsImpl.setOtherInfo(StringUtils.isNotBlank(byUsername.getOtherInfo()) ? byUsername.getOtherInfo() : "{}");
        userDetailsImpl.setDescription(byUsername.getDescription());
        userDetailsImpl.setIsEnable(byUsername.getIsEnable());
        userDetailsImpl.setRealName(byUsername.getRealName());

        UserRoleEntity userRole = userRoleRepository.findByUserId(byUsername.getId());
        if (null != userRole) {
            List<RolePermissionEntity> rolePermissionEntityList = rolePermissionRepository.findAllByRoleId(userRole.getRoleId());
            if (CollectionUtils.isNotEmpty(rolePermissionEntityList)) {
                Set<Long> permissionIdSet = rolePermissionEntityList.stream().map(RolePermissionEntity::getPermissionId).collect(Collectors.toSet());
                List<PermissionEntity> permissionEntities = permissionRepository.findAllById(permissionIdSet);
                Set<Long> scopeIdSet = permissionEntities.stream().map(PermissionEntity::getScopeId).collect(Collectors.toSet());
                Map<Long, ScopeEntity> scopeEntityMap = new HashMap<>(scopeIdSet.size());
                scopeRepository.findAllById(scopeIdSet).forEach(x -> scopeEntityMap.put(x.getId(), x));
                Set<Long> resourceIdSet = permissionEntities.stream().map(PermissionEntity::getResourceId).collect(Collectors.toSet());
                List<ResourceEntity> resourceEntities = resourceRepository.findAllById(resourceIdSet);
                Map<Long, ResourceEntity> resourceEntityMap = new HashMap<>(resourceEntities.size());
                resourceEntities.forEach(x -> resourceEntityMap.put(x.getId(), x));
                List<PermissionVO> permissionVOList = permissionEntities.stream().map(x -> permissionTransfer.entity2Dto(x, false)).collect(Collectors.toList());
                permissionVOList.forEach(x -> x.setScopeName(scopeEntityMap.get(x.getScopeId()).getScope()));
                permissionVOList.forEach(x -> x.setResourceName(resourceEntityMap.get(x.getResourceId()).getResourceId()));
                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = permissionVOList.stream().map(scope -> new SimpleGrantedAuthority(scope.getScopeName())).collect(Collectors.toSet());
                userDetailsImpl.setGrantedAuthorities(simpleGrantedAuthorities);
                userDetailsImpl.setResourceIds(resourceEntities.stream().map(ResourceEntity::getResourceId).collect(Collectors.toSet()));
            }
        } else {
            userDetailsImpl.setGrantedAuthorities(new ArrayList<>(0));
        }
        return userDetailsImpl;
    }

    /**
     * 分页查询用户
     * @param pageReq 分页查询请求参数
     * @return 分页结果
     */
    @Override
    public TableData<UserVO> page(PageReqVO<QueryUserVO> pageReq) {
        Sort.Order order = Sort.Order.asc("username");
        Pageable pageable = getPageRequest(pageReq);
        Page<UserEntity> entityPage = userRepository.findAll(new UserSpecification(pageReq.getData()), pageable);
        List<UserVO> dtoList = entityPage.getContent().stream().map(input -> CopyUtil.copyProperties(input, new UserVO())).collect(Collectors.toList());
        TableData<UserVO> tableData = new TableData<>();
        tableData.setContent(dtoList);
        tableData.setPage(entityPage.getNumber() + 1);
        tableData.setSize(entityPage.getSize());
        tableData.setTotalPages(entityPage.getTotalPages());
        tableData.setTotals(entityPage.getTotalElements());
        return tableData;
    }

    /**
     * 新增用户
     * @param createUserVO 新增用户请求对象
     * @return 新增结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<CreateUserVO> add(CreateUserVO createUserVO) {
        UserEntity userEntity = CopyUtil.copyProperties(createUserVO, new UserEntity());
        // 密码加密
        userEntity.setPassword(ApplicationContextUtil.getBean(PasswordEncoder.class).encode(userEntity.getPassword()));
        // 默认启用
        userEntity.setIsEnable(Boolean.TRUE);
        userEntity.setCreateTime(LocalDateTime.now());
        userRepository.save(userEntity);
        createUserVO.setId(userEntity.getId());
        return createResult(SUCCESS, "成功", createUserVO);
    }

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    @Override
    public CommonResult<ShowUpdateUserVO> findByUsername(String username) {
        UserEntity byUsername = userRepository.findByUsername(username);
        CommonResult<ShowUpdateUserVO> result;
        ShowUpdateUserVO showUpdateUserVO = new ShowUpdateUserVO();
        if(null != byUsername) {
            UserRoleEntity byUserId = userRoleRepository.findByUserId(byUsername.getId());
            if(null != byUserId) {
                Optional<RoleEntity> byId = roleRepository.findById(byUserId.getRoleId());
                byId.ifPresent(role -> {
                    showUpdateUserVO.setRoleId(role.getId());
                    showUpdateUserVO.setRoleName(role.getName());
                });
            }
            showUpdateUserVO.setId(byUsername.getId());
            showUpdateUserVO.setUsername(byUsername.getUsername());
            showUpdateUserVO.setPassword(byUsername.getPassword());
            showUpdateUserVO.setIsEnable(byUsername.getIsEnable());
            showUpdateUserVO.setRealName(byUsername.getRealName());
            showUpdateUserVO.setDescription(byUsername.getDescription());
            showUpdateUserVO.setCreateTime(byUsername.getCreateTime());
            showUpdateUserVO.setUpdateTime(byUsername.getUpdateTime());
            showUpdateUserVO.setInner(byUsername.getInner());
            showUpdateUserVO.setOtherInfo(byUsername.getOtherInfo());

            result = createResult(SUCCESS, "成功!", showUpdateUserVO);
        } else {
            result = createResult(ERROR, "失败!", null);
        }
        return result;
    }

    /**
     * 根据ID删除用户
     * @param id ID
     * @return 删除结果
     */
    @Override
    public CommonResult<Long> delete(Long id) {
        if(Objects.isNull(id)) {
            return createResult(PARAM_ERROR, "用户ID不能为空!", null);
        }
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isPresent() && byId.get().getInner()) {
            userRepository.deleteById(id);
        }
        // TODO 删除用户，同时删除用户相关的其他信息
        return createResult(SUCCESS, "成功!", id);
    }

    /**
     * 修改用户
     * @param updateUserVO 修改用户请求对象
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<UpdateUserVO> update(UpdateUserVO updateUserVO) {
        // 保存用户和角色的关系
        UserRoleEntity byUserId = userRoleRepository.findByUserId(updateUserVO.getId());
        UserRoleEntity userRoleEntity;
        if(null == byUserId) {
            userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(updateUserVO.getId());
            userRoleEntity.setRoleId(updateUserVO.getRoleId());
            userRoleEntity.setCreateMan(UserContextHolder.currentUser().getUsername());
            userRoleEntity.setCreateTime(LocalDateTime.now());
            userRoleRepository.save(userRoleEntity);
        } else {
            byUserId.setRoleId(updateUserVO.getRoleId());
            byUserId.setModifyTime(LocalDateTime.now());
            byUserId.setModifyMan(UserContextHolder.currentUser().getUsername());
            userRoleRepository.save(byUserId);
        }
        // 调用用户服务修改用户
        Optional<UserEntity> byId = userRepository.findById(updateUserVO.getId());
        if(!byId.isPresent()) {
            return createResult(DATA_NOT_EXIST, "该用户不存在!", updateUserVO);
        }
        UserEntity oldUser = byId.get();
        if (!StringUtils.equals(oldUser.getUsername(), updateUserVO.getUsername())) {
            return createResult(ERROR, "用户名不能修改", updateUserVO);
        }
        oldUser.setUpdateTime(LocalDateTime.now());
        oldUser.setRealName(updateUserVO.getRealName());
        oldUser.setIsEnable(updateUserVO.getIsEnable());
        oldUser.setDescription(updateUserVO.getDescription());
        userRepository.save(oldUser);
        return createResult(SUCCESS, "成功!", updateUserVO);
    }

    /**
     * 用户登录
     * @param userLoginVO 用户登录请求对象
     * @return 登录结果
     */
    @Override
    public CommonResult<UserInfoVO> login(UserLoginVO userLoginVO) {
        return baseLogin(userLoginVO);
    }

    /**
     * 用户登出
     * @param request HttpServletRequest
     * @return 登出结果
     */
    @Override
    public CommonResult<String> logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization)) {
            return createResult(ERROR, "失败", "没有token信息");
        } else {
            String token = StringUtils.substringBetween(authorization, "Bearer ");
            RedisTokenStore redisTokenStore = ApplicationContextUtil.getBean(RedisTokenStore.class);
            redisTokenStore.removeAccessToken(token);
            return createResult(SUCCESS, "成功", "登出成功");
        }
    }

    private CommonResult<UserInfoVO> baseLogin(UserLoginVO vo) {
        UserDetailsImpl userDetails = (UserDetailsImpl) loadUserByUsername(vo.getUsername());
        // 判断用户密码是否正确
        if (!ApplicationContextUtil.getBean(PasswordEncoder.class).matches(vo.getPassword(), userDetails.getPassword())) {
            return createResult(FORBIDDEN, "用户名或密码错误", null);
        }

        // 用户的权限
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Set<String> scope = CollectionUtils.isNotEmpty(authorities) ? authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()) : Sets.newHashSet();
        Set<String> resourceIds = CollectionUtils.isNotEmpty(userDetails.getResourceIds()) ? userDetails.getResourceIds() : Sets.newHashSet();
        return createResult(SUCCESS, "成功", new UserInfoVO(userDetails.getId(), userDetails.getUsername(), null, scope, menuService.getMenus(scope, resourceIds), "", JSON.parseObject(userDetails.getOtherInfo(), Map.class), menuService.getButtons(scope, resourceIds)));
    }
}
