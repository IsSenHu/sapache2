package com.ssaw.ssawmehelper.dao.po.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author HuSen
 * @date 2019/4/16 9:34
 */
@Data
@TableName("tb_commit_leave")
public class CommitLeavePO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("bn")
    private String bn;

    @TableField("begin_time")
    private String beginTime;

    @TableField("end_time")
    private String endTime;

    @TableField("leave_time")
    private BigDecimal leaveTime;

    @TableField("leave_days")
    private BigDecimal leaveDays;

    @TableField("success")
    private Boolean success;
}