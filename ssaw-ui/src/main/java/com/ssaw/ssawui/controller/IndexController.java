package com.ssaw.ssawui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author HuSen.
 * @date 2018/12/6 15:12.
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    private ModelAndView index() {
        return new ModelAndView("/index");
    }

    @GetMapping("/user")
    private Principal principal(Principal user) {
        return user;
    }
}
