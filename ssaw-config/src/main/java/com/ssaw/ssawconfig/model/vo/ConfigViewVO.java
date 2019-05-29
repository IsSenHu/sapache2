package com.ssaw.ssawconfig.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author HuSen
 * @date 2019/5/28 18:17
 */
@Data
public class ConfigViewVO {

    /**
     * 主键
     * */
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
     * 配置文件类型
     * */
    private String type;

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