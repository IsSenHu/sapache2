package com.ssaw.job.admin;

import com.ssaw.commons.util.app.ApplicationContextUtil;
import com.ssaw.job.admin.dao.mapper.BasePackageClasses;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author HuSen
 * @date 2019/3/15 14:54
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackageClasses = BasePackageClasses.class)
public class AdminApplication {

    public static void main(String[] args) {
        log.info("分布式任务调度中心管理后台启动中.");
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AdminApplication.class, args);
        ApplicationContextUtil.setContext(applicationContext);
        log.info("分布式任务调度中心管理后台启动成功.");
    }
}