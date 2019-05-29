package com.ssaw.ssawconfig;

import com.ssaw.commons.enable.EnableAutoRequestResolve;
import com.ssaw.ssawauthenticatecenterfeign.annotations.EnableAutoAuthenticateInfo;
import com.ssaw.ssawauthenticatecenterfeign.annotations.EnableUaa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author HuSen
 */
@EnableUaa
@EnableAutoAuthenticateInfo
@EnableAutoRequestResolve
@EnableConfigServer
@SpringBootApplication
public class SsawConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsawConfigApplication.class, args);
    }

}
