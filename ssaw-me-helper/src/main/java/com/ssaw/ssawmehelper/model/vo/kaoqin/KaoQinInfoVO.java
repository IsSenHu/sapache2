package com.ssaw.ssawmehelper.model.vo.kaoqin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/20 14:58
 */
@Data
public class KaoQinInfoVO implements Serializable {
    private static final long serialVersionUID = -649874888122502900L;

    /**
     * 打卡开始时间
     */
    @JSONField(name = "K_DAY_CARD_BEGIN")
    private String cardBegin;

    @JSONField(name = "K_DAY_OUT_COUNT")
    private String outCount;

    @JSONField(name = "K_DAY_K_DAYA0191")
    private String kDay0191;

    @JSONField(name = "VIEW_A01_B01_DEPT_CODE")
    private String viewA01B01DeptCode;

    @JSONField(name = "K_DAY_ONDUTY_TIME")
    private String onDutyTime;

    /**
     * 已审核加班调休数
     */
    @JSONField(name = "K_DAY_CARD_SORT_HOURS")
    private String cardSortHours;

    /**
     * 出勤状况
     */
    @JSONField(name = "K_DAY_YCQK")
    private String dayYcqk;

    @JSONField(name = "K_DAY_K_MEMORY")
    private String dayKMemory;

    @JSONField(name = "K_DAY_SHIFT_NO")
    private String dayShiftNo;

    @JSONField(name = "K_DAY_K_ID")
    private String kId;

    @JSONField(name = "KqOverStatus")
    private String kqOverStatus;

    @JSONField(name = "KqLeaveStatus")
    private String kqLeaveStatus;

    @JSONField(name = "K_DAY_YCQ")
    private String ycq;

    @JSONField(name = "K_DAY_LATE_MIN")
    private String lateMin;

    @JSONField(name = "K_DAY_LEAVE_TIME")
    private String leaveTime;

    @JSONField(name = "K_DAY_K_DONE")
    private String kDone;

    @JSONField(name = "K_DAY_EARLY_MIN")
    private String earlyMin;

    @JSONField(name = "K_DAY_QJXX")
    private String qjxx;

    @JSONField(name = "K_DAY_CARD_END")
    private String cardEnd;

    @JSONField(name = "K_DAY_ABSENT_COUNT")
    private String absentCount;

    @JSONField(name = "K_DAY_ABSENT_TIME")
    private String absentTime;

    @JSONField(name = "K_DAY_A0188")
    private String a0188;

    @JSONField(name = "VIEW_A01_A0190")
    private String viewA01A0190;

    @JSONField(name = "K_DAY_K_LEAVE_TYPE")
    private String kLeaveType;

    @JSONField(name = "K_DAY_K_DAY053")
    private String kDay053;

    @JSONField(name = "K_DAY_K_DAY051")
    private String kDay051;

    @JSONField(name = "K_DAY_OVER_TYPE")
    private String overType;

    @JSONField(name = "K_DAY_K_SJZG")
    private String kSjzg;

    @JSONField(name = "K_DAY_OUT_TIME2")
    private String outTime2;

    @JSONField(name = "K_DAY_OUT_TIME1")
    private String outTime1;

    @JSONField(name = "K_DAY_K_ACTIONTIME")
    private String kActionTime;

    @JSONField(name = "K_DAY_DUTY_DATE")
    private String dutyDate;

    @JSONField(name = "K_DAY_SHIFT_NAME")
    private String shiftName;

    @JSONField(name = "K_DAY_OUT_TIME")
    private String outTime;

    @JSONField(name = "K_DAY_LEAVE_COUNT")
    private String leaveCount;

    @JSONField(name = "K_DAY_LEAVE_TIME1")
    private String leaveTime1;

    @JSONField(name = "K_DAY_K_DAYSTATE")
    private String kDayState;

    @JSONField(name = "K_DAY_LEAVE_TIME3")
    private String leaveTime3;

    @JSONField(name = "K_DAY_K_ACTIONA0188")
    private String kActionA0188;

    @JSONField(name = "K_DAY_SIGNED")
    private String signed;

    @JSONField(name = "K_DAY_LEAVE_TIME2")
    private String leaveTime2;

    /** 是否上线 */
    private Boolean online;

    /** 是否忘记打卡 */
    private Boolean forget;

    /** 是否已提交加班申请 */
    private Boolean commitOverTime;

    /** 是否已提交调休申请 */
    private Boolean commitLeave;
}