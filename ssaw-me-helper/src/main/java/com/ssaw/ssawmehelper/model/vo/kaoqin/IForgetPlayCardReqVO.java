package com.ssaw.ssawmehelper.model.vo.kaoqin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/4/12 17:38
 */
@Data
public class IForgetPlayCardReqVO implements Serializable {
    private static final long serialVersionUID = -8399996232812078992L;

    /** 考勤系统帐号 */
    private String bn;

    /** 考勤记录时间 */
    private String dutyTime;
}