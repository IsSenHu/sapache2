package com.ssaw.ssawauthenticatecenterfeign.vo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author HuSen
 * @date 2019/2/28 17:37
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleUserAttributeVO implements Serializable {
    private static final long serialVersionUID = 8017196926059225733L;

    /** ID */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /** 用户名 */
    private String username;
    /** 真实姓名 */
    private String realName;
    /** 用户描述 */
    private String description;
    /** 是否可用 */
    private Boolean isEnable;
    /** 其它信息 */
    private String otherInfo;
    /** 权限 */
    private Set<String> permissions;
}