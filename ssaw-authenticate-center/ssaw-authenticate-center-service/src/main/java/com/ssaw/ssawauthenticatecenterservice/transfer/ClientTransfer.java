package com.ssaw.ssawauthenticatecenterservice.transfer;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.ClientDetailsInfoVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.ClientVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.client.ClientDetailsEntity;
import com.ssaw.ssawauthenticatecenterservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;
import static org.apache.commons.lang.StringUtils.join;

/**
 * @author HuSen.
 * @date 2019/1/4 13:48.
 */
@Component
public class ClientTransfer {

    private final UserService userService;

    @Autowired
    public ClientTransfer(UserService userService) {
        this.userService = userService;
    }

    public ClientDetailsEntity dto2Entity(ClientVO dto) {
        ClientDetailsEntity entity = null;
        if(null != dto) {
            entity = new ClientDetailsEntity();
            entity.setClientId(dto.getClientId());
            entity.setAccessTokenValiditySeconds(dto.getAccessTokenValiditySeconds());
            entity.setAuthorizedGrantTypes(dto.getAuthorizedGrantTypes());
            entity.setClientSecret(dto.getClientSecret());
            entity.setCreateMan(dto.getCreateMan());
            entity.setCreateTime(dto.getCreateTime());
            entity.setRefreshTokenValiditySeconds(dto.getRefreshTokenValiditySeconds());
            entity.setRegisteredRedirectUris(dto.getRegisteredRedirectUris());
            entity.setResourceIds(dto.getResourceIds());
            entity.setScopes(dto.getScopes());
            entity.setUpdateMan(dto.getUpdateMan());
            entity.setUpdateTime(dto.getUpdateTime());
            entity.setUserId(dto.getUserId());
        }
        return entity;
    }

    public ClientVO entity2Dto(ClientDetailsEntity entity) {
        ClientVO clientVO = null;
        if(null != entity) {
            clientVO = new ClientVO();
            clientVO.setClientId(entity.getClientId());
            clientVO.setUserId(entity.getUserId());
            clientVO.setClientSecret(entity.getClientSecret());
            clientVO.setResourceIds(join(entity.getResourceIds().toArray(new String[0]), ","));
            clientVO.setScopes(join(entity.getScope().toArray(new String[0]), ","));
            clientVO.setAuthorizedGrantTypes(join(entity.getAuthorizedGrantTypes().toArray(new String[0]), ","));
            clientVO.setRegisteredRedirectUris(join(entity.getRegisteredRedirectUri().toArray(new String[0]), ","));
            clientVO.setAccessTokenValiditySeconds(entity.getAccessTokenValiditySeconds());
            clientVO.setRefreshTokenValiditySeconds(entity.getRefreshTokenValiditySeconds());
            clientVO.setCreateTime(entity.getCreateTime());
            clientVO.setUpdateTime(entity.getUpdateTime());
            clientVO.setCreateMan(entity.getCreateMan());
            clientVO.setUpdateMan(entity.getUpdateMan());
        }
        return clientVO;
    }

    public ClientDetailsInfoVO entity2DetailsInfoDto(ClientDetailsEntity entity) {
        ClientDetailsInfoVO infoDto = null;
        if(null != entity) {
            infoDto = new ClientDetailsInfoVO();
            infoDto.setClientId(entity.getClientId());
            infoDto.setUserId(entity.getUserId());
            CommonResult<UserVO> byId = userService.findById(entity.getUserId());
            if(byId.getCode() == SUCCESS) {
                infoDto.setUsername(byId.getData().getUsername());
            }
            infoDto.setClientSecret(entity.getClientSecret());
            infoDto.setResourceIds(join(entity.getResourceIds().toArray(new String[0])));
            infoDto.setScopes(join(entity.getScope().toArray(new String[0])));
            infoDto.setAuthorizedGrantTypes(join(entity.getAuthorizedGrantTypes().toArray(new String[0])));
            infoDto.setRegisteredRedirectUris(join(entity.getRegisteredRedirectUri().toArray(new String[0])));
            infoDto.setAccessTokenValiditySeconds(entity.getAccessTokenValiditySeconds());
            infoDto.setRefreshTokenValiditySeconds(entity.getRefreshTokenValiditySeconds());
            infoDto.setCreateTime(entity.getCreateTime());
            infoDto.setUpdateTime(entity.getUpdateTime());
            infoDto.setCreateMan(entity.getCreateMan());
            infoDto.setUpdateMan(entity.getUpdateMan());
        }
        return infoDto;
    }
}
