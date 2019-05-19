package com.ssaw.ssawauthenticatecenterfeign.vo.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author HuSen.
 * @date 2018/12/11 14:35.
 */
@Data
public class CreateClientVO implements Serializable {

    private static final long serialVersionUID = 1446307040523759848L;
    /** clientId、appKey */
    @NotBlank(message = "ClientId不能为空!")
    @Length(max = 30, message = "ClientId长度不得大于30")
    private String clientId;

    /** 用户ID */
    @NotNull(message = "用户ID不能为空!")
    private Long userId;

    /** clientSecret */
    @NotBlank(message = "ClientSecret不能为空!")
    @Length(min = 8, max = 30, message = "ClientSecret长度在8到30之间!")
    private String clientSecret;

    /** 资源ID 如果为空，则表示不验证资源ID */
    private String resourceIds;

    /** 作用域 */
    private String scopes;

    /** oauth2授权方式 */
    @NotBlank(message = "授权方式不能为空!")
    private String authorizedGrantTypes;

    /** 注册应用时的回调地址 */
    @NotBlank(message = "回调地址不能为空!")
    private String registeredRedirectUris;

    /** access token 过期时间 */
    private Integer accessTokenValiditySeconds;

    /** refresh token 过期时间 */
    private Integer refreshTokenValiditySeconds;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createMan;

    /**
     * 修改人
     */
    private String updateMan;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
