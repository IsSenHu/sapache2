package com.ssaw.ssawauthenticatecenterfeign.vo.scope;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/1 13:59
 */
@Data
public class QueryScopeVO implements Serializable {
    private static final long serialVersionUID = -4921933836709467289L;

    private String scope;

    private Long resourceId;
}