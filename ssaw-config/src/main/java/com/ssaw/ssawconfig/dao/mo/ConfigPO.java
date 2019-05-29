package com.ssaw.ssawconfig.dao.mo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author HuSen
 * @date 2019/5/28 18:24
 */
@Data
@Document(collection = "apps")
public class ConfigPO {

    /**
     * 主键
     * */
    @Id
    private String id;

    /**
     * 应用名称
     * */
    private String application;

    /**
     * 环境
     * */
    private String profile;

    /**
     * 标签
     * */
    private String label;

    /**
     * 配置详情
     * */
    private String config;

    /**
     * 修改时间
     * */
    private LocalDateTime modifyTime;

    /**
     * 修改人
     * */
    private String modifyMan;

    /**
     * 创建时间
     * */
    private LocalDateTime createTime;

    /**
     * 创建人
     * */
    private String createMan;
}