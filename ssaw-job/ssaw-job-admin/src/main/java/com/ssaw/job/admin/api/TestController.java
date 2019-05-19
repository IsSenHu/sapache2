package com.ssaw.job.admin.api;

import com.ssaw.job.admin.dao.mapper.test.TestMapper;
import com.ssaw.job.admin.dao.po.TestPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuSen
 * @date 2019/3/15 15:28
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @GetMapping("/{name}")
    public TestPO test(@PathVariable(name = "name") String name) {
        return testMapper.findByFirstName(name);
    }
}