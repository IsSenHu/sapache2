package com.ssaw.ssawdataelasticsearchdemo.controller;

import com.ssaw.ssawdataelasticsearchdemo.document.CatDocument;
import com.ssaw.ssawdataelasticsearchdemo.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuSen
 * @date 2019/3/8 11:15
 */
@RestController
@RequestMapping("/api")
public class DemoController {
    private final CatRepository catRepository;

    @Autowired
    public DemoController(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @GetMapping("/demo")
    public void contextLoads() {
        for (long i = 1; i <= 10000; i++) {
            CatDocument catDocument = new CatDocument();
            catDocument.setId(i);
            catDocument.setFirstName("伤");
            catDocument.setLastName("不起" + i);
            catDocument.setAge((int) (i % 100));
            catRepository.save(catDocument);
        }
    }
}