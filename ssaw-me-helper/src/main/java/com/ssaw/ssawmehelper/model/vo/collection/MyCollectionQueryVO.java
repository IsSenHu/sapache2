package com.ssaw.ssawmehelper.model.vo.collection;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/4/23 22:27
 */
@Data
public class MyCollectionQueryVO implements Serializable {
    private static final long serialVersionUID = 4112981389142975722L;

    private Boolean byTime;

    private String classification;
}