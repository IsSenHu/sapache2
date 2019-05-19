package com.ssaw.ssawauthenticatecenterfeign.vo.resource;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/1 13:39
 */
@Data
public class CreateResourceVO implements Serializable {
    private static final long serialVersionUID = -6578233473793545622L;

    /**
     * 资源ID 和服务名相同
     */
    private String resourceId;

    /**
     * 服务描述
     */
    private String description;
}