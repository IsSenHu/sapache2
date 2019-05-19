package com.ssaw.ssawauthenticatecenterfeign.vo.permission;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author HuSen.
 * @date 2018/12/13 17:11.
 */
@Data
public class CreatePermissionVO implements Serializable {
    private static final long serialVersionUID = -5913607680246463639L;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long id;

    /** 权限名字 */
    @NotBlank(message = "权限名字不能为空!")
    private String name;

    /** 所关联作用域ID */
    @NotNull(message = "所属作用域不能为空!")
    private Long scopeId;

    private String scopeName;

    /** 所关联资源ID */
    private Long resourceId;

    private String resourceName;

    /** 描述 */
    private String description;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private LocalDateTime createTime;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private LocalDateTime modifyTime;
    private String createMan;
    private String modifyMan;
}
