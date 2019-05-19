package com.ssaw.ssawmehelper.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.ssaw.ssawmehelper.dao.mapper.BasicPackagesClass;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis 配置
 * @author HuSen
 * @date 2019/02/15
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackageClasses = BasicPackagesClass.class)
public class MybatisConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
