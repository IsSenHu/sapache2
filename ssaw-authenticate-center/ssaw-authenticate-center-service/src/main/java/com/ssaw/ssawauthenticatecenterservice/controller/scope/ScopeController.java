package com.ssaw.ssawauthenticatecenterservice.controller.scope;

import com.ssaw.commons.annotations.RequestLog;
import com.ssaw.commons.annotations.Validating;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.QueryScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.CreateScopeVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.UpdateScopeVO;
import com.ssaw.ssawauthenticatecenterservice.controller.BaseController;
import com.ssaw.ssawauthenticatecenterservice.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/12 11:33.
 */
@RestController
@RequestMapping("/api/scope")
@SecurityApi(index = "1", group = "授权管理", menu = @Menu(index = "1-2", title = "作用域", scope = "作用域管理", to = "/authenticate/center/scope"))
public class ScopeController extends BaseController {

    private final ScopeService scopeService;

    @Autowired
    public ScopeController(ApplicationContext context, ScopeService scopeService) {
        super(context);
        this.scopeService = scopeService;
    }

    /**
     * 新增作用域
     * @param createScopeVO 新增作用域请求对象
     * @return 新增结果
     */
    @Validating
    @PostMapping("/add")
    @RequestLog(desc = "新增作用域")
    @SecurityMethod(antMatcher = "/api/scope/add", scope = "创建作用域", button = "SCOPE_CREATE", buttonName = "添加")
    public CommonResult<CreateScopeVO> add(@RequestBody CreateScopeVO createScopeVO) {
        return scopeService.add(createScopeVO);
    }

    /**
     * 分页查询作用域
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    @PostMapping("/page")
    @RequestLog(desc = "分页查询作用域")
    @SecurityMethod(antMatcher = "/api/scope/page", scope = "读取作用域信息", button = "SCOPE_READ", buttonName = "搜索")
    public TableData<ScopeVO> page(@RequestBody PageReqVO<QueryScopeVO> pageReqVO) {
        return scopeService.page(pageReqVO);
    }

    /**
     * 根据ID删除作用域
     * @param id ID
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    @RequestLog(desc = "根据ID删除作用域")
    @SecurityMethod(antMatcher = "/api/scope/delete/*", scope = "删除作用域", button = "SCOPE_DELETE", buttonName = "删除")
    public CommonResult<Long> delete(@PathVariable(name = "id") Long id) {
        return scopeService.delete(id);
    }

    /**
     * 根据ID查询作用域
     * @param id ID
     * @return 作用域
     */
    @GetMapping("/findById/{id}")
    @RequestLog(desc = "根据ID查询作用域")
    @SecurityMethod(antMatcher = "/api/scope/findById/*", scope = "读取作用域信息")
    public CommonResult<ScopeVO> findById(@PathVariable(name = "id") Long id) {
        return scopeService.findById(id);
    }

    /**
     * 修改作用域
     * @param updateScopeVO 修改作用域请求对象
     * @return 修改结果
     */
    @Validating
    @PostMapping("/update")
    @RequestLog(desc = "修改作用域请求对象")
    @SecurityMethod(antMatcher = "/api/scope/update", scope = "修改作用域", button = "SCOPE_UPDATE", buttonName = "编辑")
    public CommonResult<UpdateScopeVO> update(@RequestBody UpdateScopeVO updateScopeVO) {
        return scopeService.update(updateScopeVO);
    }

    /**
     * 根据作用域名称搜索作用域
     * @param scope 作用域名称
     * @return 作用域
     */
    @GetMapping("/search/{scope}")
    @RequestLog(desc = "根据作用域名称搜索作用域")
    @SecurityMethod(antMatcher = "/api/scope/search/*", scope = "读取作用域信息")
    public CommonResult<List<ScopeVO>> search(@PathVariable(name = "scope") String scope) {
        return scopeService.search(scope);
    }

    /**
     * 根据作用域名称搜索作用域
     * @param scope 作用域名称
     * @return 作用域
     */
    @GetMapping("/searchForUpdate/{scope}")
    @RequestLog(desc = "ScopeController.searchForUpdate(Long scopeId)")
    @SecurityMethod(antMatcher = "/api/scope/searchForUpdate/*", scope = "读取作用域信息")
    public CommonResult<List<ScopeVO>> searchForUpdate(@PathVariable(name = "scope") String scope) {
        return scopeService.searchForUpdate(scope);
    }
}
