package com.ssaw.ssawauthenticatecenterservice.service.impl;

import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.security.util.SecurityUtils;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.ClientDetailsInfoVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.ClientVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.CreateClientVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.QueryClientVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.client.ClientDetailsEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.resource.ResourceEntity;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.client.ClientRepository;
import com.ssaw.ssawauthenticatecenterservice.dao.repository.resource.ResourceRepository;
import com.ssaw.ssawauthenticatecenterservice.service.ClientService;
import com.ssaw.ssawauthenticatecenterservice.specification.ClientSpecification;
import com.ssaw.ssawauthenticatecenterservice.transfer.ClientTransfer;
import com.ssaw.ssawauthenticatecenterservice.details.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssaw.commons.constant.Constants.ResultCodes.*;
import static com.ssaw.ssawauthenticatecenterservice.constants.client.ClientConstant.AuthorizedGrantTypes.*;

/**
 * @author HuSen.
 * @date 2018/12/11 9:33.
 */
@Slf4j
@Service
public class ClientServiceImpl extends BaseService implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientTransfer clientTransfer;
    private final ResourceRepository resourceRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientTransfer clientTransfer, PasswordEncoder passwordEncoder, ResourceRepository resourceRepository) {
        this.clientRepository = clientRepository;
        this.clientTransfer = clientTransfer;
        this.passwordEncoder = passwordEncoder;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        Optional<ClientDetailsEntity> optionalDetailsEntity = clientRepository.findById(clientId);
        if(!optionalDetailsEntity.isPresent()) {
            throw new InvalidClientException("This client(" + clientId + ") is invalid");
        }
        // 校验client与用户的绑定关系
        UserDetailsImpl userDetails = SecurityUtils.getUserDetails(UserDetailsImpl.class);
        ClientDetailsEntity clientDetailsEntity = optionalDetailsEntity.get();
        if(!Objects.isNull(userDetails) && !userDetails.getId().equals(clientDetailsEntity.getUserId())) {
            throw new InvalidClientException("This user(" + userDetails.getId() + ") is not match this client(" + clientId + ")");
        }
        return clientDetailsEntity;
    }

    /**
     * 根据clientId查询客户端
     * @param clientId clientId
     * @return 客户端
     */
    @Override
    public CommonResult<ClientDetailsInfoVO> findById(String clientId) {
        return clientRepository.findById(clientId)
                .map(entity -> CommonResult.createResult(SUCCESS, "成功!", clientTransfer.entity2DetailsInfoDto(entity)))
                .orElseGet(() -> CommonResult.createResult(DATA_NOT_EXIST, "客户端不存在!", null));
    }

    /**
     * 新增客户端
     * @param createClientVO 新增客户端请求对象
     * @return 新增结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<CreateClientVO> save(CreateClientVO createClientVO) {
        if(null != createClientVO) {

            // 校验ClientId是否唯一
            if(clientRepository.countByClientId(createClientVO.getClientId()) > 0) {
                return CommonResult.createResult(DATA_EXIST, "该ClientId已有!", createClientVO);
            }

            // 检查授权方式与是否刷新token是否设置合法
            String authorizedGrantTypes = createClientVO.getAuthorizedGrantTypes();
            if(authorizedGrantTypes.contains(REFRESH_TOKEN.getValue())) {
                if(!authorizedGrantTypes.contains(AUTHORIZATION_CODE.getValue()) && !authorizedGrantTypes.contains(PASSWORD.getValue())) {
                    return CommonResult.createResult(PARAM_ERROR, "授权方式设置错误，设置的授权类型不支持refresh_token", createClientVO);
                }
            }

            // 将资源的id转为resourceId
            List<ResourceEntity> allById = resourceRepository.findAllById(Arrays.stream(createClientVO.getResourceIds().split(",")).map(Long::valueOf).collect(Collectors.toList()));
            String resourceIds = allById.stream().map(ResourceEntity::getResourceId).collect(Collectors.joining(","));
            createClientVO.setResourceIds(resourceIds);

            // 加密密码
            createClientVO.setClientSecret(passwordEncoder.encode(createClientVO.getClientSecret()));
            // 创建时间
            createClientVO.setCreateTime(LocalDateTime.now());
            // token过期时间
            createClientVO.setAccessTokenValiditySeconds(null == createClientVO.getAccessTokenValiditySeconds() ? 60 * 60 * 24 * 30 : createClientVO.getAccessTokenValiditySeconds());
            // refresh token过期时间
            createClientVO.setRefreshTokenValiditySeconds(null == createClientVO.getRefreshTokenValiditySeconds() ? 60 * 60 * 24 * 30 : createClientVO.getRefreshTokenValiditySeconds());

            ClientDetailsEntity entity = CopyUtil.copyProperties(createClientVO, new ClientDetailsEntity());
            entity.setCreateMan(UserContextHolder.currentUser().getUsername());
            clientRepository.save(entity);
            return CommonResult.createResult(SUCCESS, "成功!", createClientVO);
        }
        return CommonResult.createResult(PARAM_ERROR, "参数错误!", null);
    }

    /**
     * 分页查询客户端
     * @param pageReq 分页请求参数
     * @return 查询结果
     */
    @Override
    public TableData<ClientVO> page(PageReqVO<QueryClientVO> pageReq) {
        Pageable pageable = getPageRequest(pageReq);
        Page<ClientDetailsEntity> page = clientRepository.findAll(new ClientSpecification(pageReq.getData()), pageable);
        TableData<ClientVO> tableData = new TableData<>();
        tableData.setContent(page.getContent().stream().map(clientTransfer::entity2Dto).collect(Collectors.toList()));
        setTableData(page, tableData);
        return tableData;
    }

    /**
     * 根据Id删除客户端
     * @param id ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResult<String> delete(String id) {
        clientRepository.deleteById(id);
        return CommonResult.createResult(SUCCESS, "成功!", id);
    }
}
