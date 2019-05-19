package com.ssaw.ssawmehelper.dao.ro;

import lombok.Data;

/**
 * @author HuSen
 * @date 2019/4/17 17:18
 */
@Data
public class MyCollectionRo {

    /**
     * 主键
     * */
    private Long id;

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 标题
     * */
    private String title;

    /**
     * 链接
     * */
    private String link;

    /**
     * 描述
     * */
    private String desc;

    /**
     * 收藏时间
     * */
    private Long time;

    /**
     * 浏览次数
     * */
    private Integer votes;

    /**
     * 类别
     * */
    private String classification;
}