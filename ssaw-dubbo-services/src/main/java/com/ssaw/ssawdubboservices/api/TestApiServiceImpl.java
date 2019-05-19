package com.ssaw.ssawdubboservices.api;

import com.ssaw.ssawinterface.dubbo.test.TestApiService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author HuSen.
 * @date 2019/1/21 14:53.
 */
@Slf4j
public class TestApiServiceImpl implements TestApiService {

    /**
     * 说你好
     * @return 名称
     */
    @Override
    public String sayHello(String name) {
        log.info("你好:{}", name);
        return name;
    }
}
