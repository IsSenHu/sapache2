package com.ssaw.ssawmehelper.model.vo.collection;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/4/23 15:07
 */
@Data
public class MyCollectionVO implements Serializable {
    private static final long serialVersionUID = 3825871055839502664L;

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