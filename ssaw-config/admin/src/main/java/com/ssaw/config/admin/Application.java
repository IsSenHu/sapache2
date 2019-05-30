package com.ssaw.config.admin;

import com.ssaw.commons.enable.EnableAutoRequestResolve;
import com.ssaw.config.sdk.annotation.EnableConfig;
import com.ssaw.ssawauthenticatecenterfeign.annotations.EnableAutoAuthenticateInfo;
import com.ssaw.ssawauthenticatecenterfeign.annotations.EnableUaa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author HuSen
 * @date 2019/5/30 16:42
 */
@EnableConfig
@EnableAutoRequestResolve
@EnableUaa
@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoAuthenticateInfo
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}