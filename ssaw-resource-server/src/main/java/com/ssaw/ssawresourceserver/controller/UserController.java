package com.ssaw.ssawresourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuSen.
 * @date 2018/11/26 10:52.
 */
@RestController
@RequestMapping("/api/userInfo")
public class UserController {

    @GetMapping("/get")
    private Map<String, String> get() {
        Map<String, String> map = new HashMap<>(1);
        map.put("username", "admin");
        return map;
    }
}
