package com.ssaw.ssawdubboservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class DubboServicesApplication {

    public static void main(String[] args) {
        new SpringApplication(new ClassPathXmlApplicationContext("classpath:application-dubbo.xml"), DubboServicesApplication.class).run(args);
    }
}

