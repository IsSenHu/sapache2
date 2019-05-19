package com.ssaw.ssawauthenticatecenterfeign.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/2/27 12:58
 */
@Data
public class ButtonVO implements Serializable {
    private static final long serialVersionUID = -6266860493489128106L;

    private String key;
    private String scope;
    private String name;
}