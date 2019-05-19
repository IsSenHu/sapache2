package com.ssaw.ssawauthenticatecenterfeign.vo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求VO
 * @author HuSen
 * @date 2019/02/13
 */
@Data
public class UserLoginVO implements Serializable {

    private static final long serialVersionUID = 1936673910594493650L;
    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;
}
