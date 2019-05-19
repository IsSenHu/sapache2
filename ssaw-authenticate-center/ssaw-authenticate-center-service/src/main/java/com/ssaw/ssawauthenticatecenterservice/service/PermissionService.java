package com.ssaw.ssawauthenticatecenterservice.service;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.vo.permission.CreatePermissionVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.permission.PermissionVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.permission.QueryPermissionVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.permission.UpdatePermissionVO;

/**
 * @author HuSen.
 * @date 2018/12/13 17:04.
 */
public interface PermissionService {
    /**
     * 新增权限
     * @param createPermissionVO 新增权限请求对象
     * @return 新增结果
     */
    CommonResult<CreatePermissionVO> add(CreatePermissionVO createPermissionVO);

    /**
     * 分页查询权限
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    TableData<PermissionVO> page(PageReqVO<QueryPermissionVO> pageReqVO);

    /**
     * 根据ID删除权限
     * @param id ID
     * @return 删除结果
     */
    CommonResult<Long> delete(Long id);

    /**
     * 修改权限
     * @param updatePermissionVO 修改请求对象
     * @return 修改结果
     */
    CommonResult<UpdatePermissionVO> update(UpdatePermissionVO updatePermissionVO);

    /**
     * 根据ID查询权限
     * @param id ID
     * @return 权限
     */
    CommonResult<PermissionVO> findById(Long id);
}
