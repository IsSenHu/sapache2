package com.ssaw.ssawauthenticatecenterfeign.vo.user;

import com.ssaw.ssawauthenticatecenterfeign.vo.ButtonVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.MenuVO;
import lombok.*;

import java.io.Serializable;
import java.util.*;

/**
 * @author HuSen
 * @date 2019/2/27 9:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 8784328800100985512L;
    /** ID */
    private Long id;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 权限 */
    private Set<String> permissions;
    /** 菜单 */
    private List<MenuVO> menuVOS;
    /** Token */
    private String token;
    /** 自定义信息 */
    private Map otherInfo;
    /** 按钮 */
    private List<ButtonVO> buttonVOS;
}