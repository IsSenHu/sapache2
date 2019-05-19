package com.ssaw.ssawauthenticatecenterfeign.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/1 10:55
 */
@Data
public class CreateUserVO implements Serializable {
    private static final long serialVersionUID = -2230514542741409837L;
    /** 主键 */
    private Long id;
    /** 用户名 */
    @NotBlank(message = "用户名不能为空!")
    @Pattern(regexp = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]{1,50}+$", message = "用户名只能由英文字母、数字、中文、下划线1到50位字符组成!")
    private String username;

    /** 密码 */
    @NotBlank(message = "密码不能为空!")
    @Pattern(regexp = ".{6,20}$", message = "密码由6-20位字符组成!")
    private String password;

    /** 是否启用 */
    private Boolean isEnable;

    /** 真实姓名 */
    private String realName;

    /** 用户描述 */
    private String description;

    /** 自定义信息 */
    private String otherInfo;

    /** 是否内部系统用户 */
    private Boolean inner;
}