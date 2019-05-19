package com.ssaw.ssawauthenticatecenterservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author HuSen.
 * @date 2018/12/14 20:17.
 */
@Setter
@Getter
public class RolePermissionReqDto implements Serializable {

    @NotNull(message = "角色ID不能为空!")
    private Long roleId;

    private String permissionIds;

}
