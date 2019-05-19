package com.ssaw.ssawmehelper.dao.ro;

import lombok.Data;

/**
 * @author HuSen
 * @date 2019/5/13 16:17
 */
@Data
public class ArticleRo {
    /**
     * 标题
     * */
    private String title;

    /**
     * 内容
     * */
    private String text;

    /**
     * 时间
     * */
    private Long timestamp;
}