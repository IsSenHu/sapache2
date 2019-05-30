package com.ssaw.config.server;

import com.ssaw.commons.enable.EnableAutoRequestResolve;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author HuSen
 * @date 2019/5/30 16:39
 */
@EnableAutoRequestResolve
@EnableConfigServer
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}