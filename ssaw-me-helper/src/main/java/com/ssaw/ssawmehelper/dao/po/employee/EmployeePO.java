package com.ssaw.ssawmehelper.dao.po.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zichun.yang
 * @date 2019-02-01
 */
@Data
@TableName("tb_employee")
public class EmployeePO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 工号 */
    @TableField("bn")
    private String bn;

    /** 员工姓名 */
    @TableField("name")
    private String name;

    /** a0188 */
    @TableField("ehr_bn")
    private String ehrBn;

    /** 接口ap */
    @TableField("ehr_ap")
    private String ehrAp;

    /** 员工绑定的系统账号 */
    @TableField("username")
    private String username;
}
