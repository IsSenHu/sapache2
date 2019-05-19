package com.ssaw.ssawauthenticatecenterfeign.vo.resource;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/1 14:10
 */
@Data
public class QueryResourceVO implements Serializable {
    private static final long serialVersionUID = -7040175876860412595L;

    private String resourceId;
}