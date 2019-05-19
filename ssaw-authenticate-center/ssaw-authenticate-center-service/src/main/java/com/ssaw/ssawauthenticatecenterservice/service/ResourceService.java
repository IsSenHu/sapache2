package com.ssaw.ssawauthenticatecenterservice.service;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.vo.UploadVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.resource.*;

import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/11 14:14.
 */
public interface ResourceService {

    /**
     * 新增资源服务
     * @param createResourceVO 新增资源服务请求对象
     * @return 新增结果
     */
    CommonResult<CreateResourceVO> add(CreateResourceVO createResourceVO);

    /**
     * 分页查询资源服务
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    TableData<ResourceVO> page(PageReqVO<QueryResourceVO> pageReqVO);

    /**
     * 根据ID查询资源服务
     * @param id ID
     * @return 资源服务
     */
    CommonResult<ResourceVO> findById(Long id);

    /**
     * 修改资源服务
     * @param updateResourceVO 修改资源请求对象
     * @return 修改结果
     */
    CommonResult<UpdateResourceVO> update(UpdateResourceVO updateResourceVO);

    /**
     * 根据ID删除资源服务
     * @param id ID
     * @return 删除结果
     */
    CommonResult<Long> delete(Long id);

    /**
     * 根据资源ID查询资源
     * @param resourceId 资源ID
     * @return 资源服务
     */
    CommonResult<List<ResourceVO>> search(String resourceId);

    /**
     * 查询所有的资源服务
     * @return 所有的资源服务
     */
    CommonResult<List<ResourceVO>> findAll();

    /**
     * 根据资源ID查询出树结构作用域
     * @param ids 资源IDS
     * @return 树结构作用域
     */
    CommonResult<EditClientScopeVO> findAllScopeByResourceIds(String ids);

    /**
     * 上传资源服务
     * @param uploadResourceVO 上传资源服务请求对象
     * @return 上传结果
     */
    CommonResult<UploadResourceVO> uploadResource(UploadResourceVO uploadResourceVO);

    /**
     * 上传资源, 作用域, 白名单, 按钮, 菜单
     * @param uploadVO 认证信息
     * @return 上传结果
     */
    CommonResult<UploadVO> uploadAuthenticateInfo(UploadVO uploadVO);
}
