package com.ssaw.ssawauthenticatecenterfeign.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuSen.
 * @date 2019/1/23 13:46.
 */
@Data
public class TreeVO implements Serializable {
    private static final long serialVersionUID = -3819164552182295152L;

    private Long id;

    private String label;

    private List<TreeVO> children;
}
