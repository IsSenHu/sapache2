package com.ssaw.ssawmehelper.service.employee;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawmehelper.dao.po.employee.EmployeePO;
import com.ssaw.ssawmehelper.model.vo.kaoqin.EmployeeRegisterReqVO;

/**
 * @author HuSen
 * @date 2019/3/20 14:21
 */
public interface EmployeeService {

    /**
     * 新增员工
     *
     * @param employeePO 员工数据模型
     * @return 新增结果
     */
    CommonResult<EmployeeRegisterReqVO> insert(EmployeePO employeePO);

    /**
     * 查询员工
     *
     * @param bn 员工编码
     * @return 员工
     */
    EmployeePO getEmployeePO(String bn);
}
