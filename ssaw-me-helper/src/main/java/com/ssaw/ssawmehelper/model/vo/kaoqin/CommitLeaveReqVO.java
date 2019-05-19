package com.ssaw.ssawmehelper.model.vo.kaoqin;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author HuSen
 * @date 2019/3/21 14:01
 */
@Data
public class CommitLeaveReqVO implements Serializable {
    private static final long serialVersionUID = -7727175575101521284L;

    private String bn;

    private String beginTime;

    private String endTime;

    private BigDecimal leaveTime;

    private BigDecimal leaveDays;

    private String dutyTime;
}