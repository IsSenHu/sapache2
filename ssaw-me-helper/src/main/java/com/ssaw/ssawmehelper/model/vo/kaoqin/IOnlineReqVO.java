package com.ssaw.ssawmehelper.model.vo.kaoqin;

import lombok.Data;

import java.io.Serializable;

/**
 * 我上线了请求数据模型
 * @author HuSen
 * @date 2019/4/12 16:20
 */
@Data
public class IOnlineReqVO implements Serializable {
    private static final long serialVersionUID = -2752343717657720381L;

    /** 考勤系统帐号 */
    private String bn;

    /** 考勤记录时间 */
    private String dutyTime;
}