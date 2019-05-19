package com.ssaw.ssawauthenticatecenterfeign.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuSen
 * @date 2019/2/25 18:28
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "ssaw.resource")
public class EnableResourceAutoProperties {

    /** 资源编码 */
    private String code;

    /** 资源ID */
    private String resourceId;

    /** 资源描述 */
    private String description;

    /** 是否注册自己 */
    private boolean self = false;

    /** 图标 */
    private String icon;

    /** 白名单 */
    private List<String> whiteList = new ArrayList<>(0);

    /** 系统白名单 */
    private List<String> systemWhiteList = new ArrayList<>(0);

    /** 静态资源路径 */
    private List<String> staticResourceList = new ArrayList<>(0);
}