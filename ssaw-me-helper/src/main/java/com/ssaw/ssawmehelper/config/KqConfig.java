package com.ssaw.ssawmehelper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuSen
 * create on 2019/7/3 10:06
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kq")
public class KqConfig {
    private String url;
}
