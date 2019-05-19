package com.ssaw.ssawauthenticatecenterfeign.vo.client;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/1 14:08
 */
@Data
public class QueryClientVO implements Serializable {
    private static final long serialVersionUID = 599599993891955452L;

    private String clientId;
}