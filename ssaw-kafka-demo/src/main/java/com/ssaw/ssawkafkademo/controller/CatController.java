package com.ssaw.ssawkafkademo.controller;

import com.ssaw.ssawkafkademo.service.DemoService;
import com.ssaw.ssawkafkademo.vo.CreateCatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuSen
 * @date 2019/3/7 11:08
 */
@RestController
@RequestMapping("/api/cat")
public class CatController {
    private final DemoService demoService;

    @Autowired
    public CatController(DemoService demoService) {
        this.demoService = demoService;
    }

    @PostMapping("/create")
    public void create(@RequestBody CreateCatVO createCatVO) {
        demoService.create(createCatVO);
    }
}