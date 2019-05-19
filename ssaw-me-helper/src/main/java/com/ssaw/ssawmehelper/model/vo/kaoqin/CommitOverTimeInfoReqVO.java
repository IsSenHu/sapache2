package com.ssaw.ssawmehelper.model.vo.kaoqin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hszyp
 */
@Data
public class CommitOverTimeInfoReqVO implements Serializable {
    private static final long serialVersionUID = -6118592579420937338L;

    private String bn;
    private Integer year;
    private Integer month;
    private Integer day;
    private String overBegin;
    private String overEnd;
    private String overTime;
    private String restTime;
    private String kqOverType;
    private String dutyTime;
}
