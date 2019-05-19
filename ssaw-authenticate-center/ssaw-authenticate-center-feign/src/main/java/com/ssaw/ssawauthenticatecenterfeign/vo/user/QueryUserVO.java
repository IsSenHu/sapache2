package com.ssaw.ssawauthenticatecenterfeign.vo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/1 13:22
 */
@Data
public class QueryUserVO implements Serializable {
    private static final long serialVersionUID = 1511631278072256636L;
    /** 主键 */
    private Long id;
    /** 用户名 */
    private String username;
}