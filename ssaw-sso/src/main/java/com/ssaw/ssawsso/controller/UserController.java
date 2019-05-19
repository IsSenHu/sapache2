package com.ssaw.ssawsso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

/**
 * @author HuSen.
 * @date 2018/12/6 20:33.
 */
@RestController
public class UserController {

    /**
     * 获取用户信息的端点
     * @param user 用户信息
     * @return 用户信息
     */
    @RequestMapping("/user")
    private Principal user(Principal user) {
        return user;
    }
}
