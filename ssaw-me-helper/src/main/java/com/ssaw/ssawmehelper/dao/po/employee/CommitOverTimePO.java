package com.ssaw.ssawmehelper.dao.po.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author HuSen
 * @date 2019/4/13 10:23
 */
@Data
@TableName("tb_commit_over_time")
public class CommitOverTimePO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("bn")
    private String bn;

    @TableField("year")
    private Integer year;

    @TableField("month")
    private Integer month;

    @TableField("day")
    private Integer day;

    @TableField("over_begin")
    private String overBegin;

    @TableField("over_end")
    private String overEnd;

    @TableField("over_time")
    private String overTime;

    @TableField("rest_time")
    private String restTime;

    @TableField("kq_over_type")
    private String kqOverType;

    @TableField("success")
    private Boolean success;
}