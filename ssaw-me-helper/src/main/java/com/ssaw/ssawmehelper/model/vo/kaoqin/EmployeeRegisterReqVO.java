package com.ssaw.ssawmehelper.model.vo.kaoqin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/20 14:25
 */
@Data
public class EmployeeRegisterReqVO implements Serializable {
    private static final long serialVersionUID = 7679490275127084779L;

    /** 员工编号 */
    private String bn;

    /** 密码 */
    private String password;
}