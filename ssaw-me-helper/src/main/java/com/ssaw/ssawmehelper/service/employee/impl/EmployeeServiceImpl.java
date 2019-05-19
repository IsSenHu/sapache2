package com.ssaw.ssawmehelper.service.employee.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawmehelper.dao.mapper.employee.EmployeeMapper;
import com.ssaw.ssawmehelper.dao.po.employee.EmployeePO;
import com.ssaw.ssawmehelper.model.vo.kaoqin.EmployeeRegisterReqVO;
import com.ssaw.ssawmehelper.service.consumption.BaseService;
import com.ssaw.ssawmehelper.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;

/**
 * @author HuSen
 * @date 2019/3/20 14:21
 */
@Service
public class EmployeeServiceImpl extends BaseService implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * 新增员工
     *
     * @param employeePO 员工数据模型
     * @return 新增结果
     */
    @Override
    public CommonResult<EmployeeRegisterReqVO> insert(EmployeePO employeePO) {
        String username = UserContextHolder.currentUser().getUsername();
        QueryWrapper<EmployeePO> queryWrapper = new QueryWrapper<EmployeePO>()
                .eq("bn", employeePO.getBn()).eq("username", username);
        EmployeePO po = employeeMapper.selectOne(queryWrapper);
        if (Objects.isNull(po)) {
            employeePO.setUsername(username);
            employeeMapper.insert(employeePO);
        } else {
            employeePO.setId(po.getId());
            employeePO.setUsername(username);
            employeeMapper.updateById(employeePO);
        }
        return CommonResult.createResult(SUCCESS, "注册成功", null);
    }

    /**
     * 查询员工
     *
     * @param bn 员工编码
     * @return 员工
     */
    @Override
    public EmployeePO getEmployeePO(String bn) {
        QueryWrapper<EmployeePO> findByBn = new QueryWrapper<>();
        findByBn.eq("bn", bn);
        List<EmployeePO> employeePOList = employeeMapper.selectList(findByBn);
        return CollectionUtils.isEmpty(employeePOList) ? null : employeePOList.get(0);
    }
}