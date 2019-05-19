package com.ssaw.ssawmehelper.model.vo.kaoqin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/20 15:19
 */
@Data
public class KaoQinInfoQueryVO implements Serializable {
    private static final long serialVersionUID = 6134231370364354286L;
    private Integer year;
    private Integer month;
}