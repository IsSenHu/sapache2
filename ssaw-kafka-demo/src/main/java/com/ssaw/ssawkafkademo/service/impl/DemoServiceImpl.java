package com.ssaw.ssawkafkademo.service.impl;

import com.ssaw.ssawkafkademo.handler.AbstractLogHandler;
import com.ssaw.ssawkafkademo.handler.LogHandler;
import com.ssaw.ssawkafkademo.service.DemoService;
import com.ssaw.ssawkafkademo.vo.CreateCatVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HuSen
 * @date 2019/3/7 10:58
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    private final LogHandler logHandler;

    @Autowired
    public DemoServiceImpl(LogHandler logHandler) {
        this.logHandler = logHandler;
    }

    @Override
    public void create(CreateCatVO createCatVO) {
        logHandler.log(createCatVO, "创建猫请求数据", "创建成功");
    }
}