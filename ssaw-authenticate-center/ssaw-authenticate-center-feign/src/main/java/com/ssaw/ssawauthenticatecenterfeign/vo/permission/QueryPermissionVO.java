package com.ssaw.ssawauthenticatecenterfeign.vo.permission;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen.
 * @date 2018/12/13 17:11.
 */
@Data
public class QueryPermissionVO implements Serializable {
    private static final long serialVersionUID = -5913607680246463639L;

    private String name;

    /** 所关联资源ID */
    private Long resourceId;
}
