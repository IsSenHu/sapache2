package com.ssaw.ssawmehelper.model.vo.collection;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/4/19 14:18
 */
@Data
public class MyCollectionCreateRequestVO implements Serializable {
    private static final long serialVersionUID = -637064075220800052L;

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
     * 类别
     * */
    private String classification;
}