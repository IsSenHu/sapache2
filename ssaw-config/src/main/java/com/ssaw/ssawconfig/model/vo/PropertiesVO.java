package com.ssaw.ssawconfig.model.vo;

import lombok.Data;

/**
 * @author HuSen
 * @date 2019/5/28 17:05
 */
@Data
public class PropertiesVO {
    private String key;
    private String value;

    public PropertiesVO(String key, String value) {
        this.key = key;
        this.value = value;
    }
}