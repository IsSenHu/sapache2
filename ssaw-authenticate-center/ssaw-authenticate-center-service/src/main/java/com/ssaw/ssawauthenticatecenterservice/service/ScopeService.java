package com.ssaw.ssawauthenticatecenterservice.service;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.QueryScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.CreateScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.UpdateScopeVO;

import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/12 11:38.
 */
public interface ScopeService {

    /**
     * 新增作用域
     * @param createScopeVO 新增作用域请求对象
     * @return 新增结果
     */
    CommonResult<CreateScopeVO> add(CreateScopeVO createScopeVO);

    /**
     * 分页查询作用域
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    TableData<ScopeVO> page(PageReqVO<QueryScopeVO> pageReqVO);

    /**
     * 根据ID删除作用域
     * @param id ID
     * @return 删除结果
     */
    CommonResult<Long> delete(Long id);

    /**
     * 根据ID查询作用域
     * @param id ID
     * @return 作用域
     */
    CommonResult<ScopeVO> findById(Long id);

    /**
     * 修改作用域
     * @param updateScopeVO 修改作用域请求对象
     * @return 修改结果
     */
    CommonResult<UpdateScopeVO> update(UpdateScopeVO updateScopeVO);

    /**
     * 根据作用域名称搜索作用域
     * @param scope 作用域名称
     * @return 作用域
     */
    CommonResult<List<ScopeVO>> search(String scope);

    /**
     * 根据作用域名称搜索作用域
     * @param scope 作用域名称
     * @return 作用域
     */
    CommonResult<List<ScopeVO>> searchForUpdate(String scope);

    /**
     * 上传作用域
     * @param scopeVOList 作用域集合
     * @param resourceId 资源主键
     * @return 上传结果
     */
    CommonResult<String> uploadScopes(Long resourceId, List<ScopeVO> scopeVOList);

    /**
     * 刷新作用域缓存
     * @param source 来源
     */
    void refreshScope(String source);
}
