package com.ssaw.ssawauthenticatecenterservice.service;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.vo.role.*;

import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/14 17:53.
 */
public interface RoleService {

    /**
     * 新增角色
     * @param createRoleVO 新增角色请求对象
     * @return 新增结果
     */
    CommonResult<CreateRoleVO> add(CreateRoleVO createRoleVO);

    /**
     * 根据ID查询角色
     * @param id ID
     * @return 角色
     */
    CommonResult<EditRoleVO> findById(Long id);

    /**
     * 分页查询角色
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    TableData<RoleVO> page(PageReqVO<QueryRoleVO> pageReqVO);

    /**
     * 修改角色
     * @param updateRoleVO 修改角色请求对象
     * @return 修改结果
     */
    CommonResult<UpdateRoleVO> update(UpdateRoleVO updateRoleVO);

    /**
     * 根据ID删除角色
     * @param id ID
     * @return 删除结果
     */
    CommonResult<Long> delete(Long id);

    /**
     * 根据角色名搜索角色
     * @param role 角色名称
     * @return 角色数据
     */
    CommonResult<List<RoleVO>> search(String role);
}
