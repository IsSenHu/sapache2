package com.ssaw.ssawui;

import com.ssaw.commons.util.app.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author HuSen
 */
@Slf4j
@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableScheduling
public class SsawUiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(SsawUiApplication.class, args);
		ApplicationContextUtil.setContext(run);
	}
}
