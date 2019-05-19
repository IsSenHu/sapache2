package com.ssaw.ssawauthenticatecenterservice.controller.role;

import com.ssaw.commons.annotations.RequestLog;
import com.ssaw.commons.annotations.Validating;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawauthenticatecenterfeign.vo.role.*;
import com.ssaw.ssawauthenticatecenterservice.controller.BaseController;
import com.ssaw.ssawauthenticatecenterservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/14 17:53.
 */
@RestController
@RequestMapping("/api/role")
@SecurityApi(index = "2", group = "用户管理", menu = @Menu(index = "2-2", title = "角色", scope = "角色管理", to = "/authenticate/center/role"))
public class RoleController extends BaseController {

    private final RoleService roleService;

    @Autowired
    public RoleController(ApplicationContext context, RoleService roleService) {
        super(context);
        this.roleService = roleService;
    }

    /**
     * 新增角色
     * @param createRoleVO 新增角色请求对象
     * @return 新增结果
     */
    @Validating
    @PostMapping("/add")
    @RequestLog(desc = "新增角色")
    @SecurityMethod(antMatcher = "/api/role/add", scope = "创建角色", button = "ROLE_CREATE", buttonName = "添加")
    public CommonResult<CreateRoleVO> add(@RequestBody CreateRoleVO createRoleVO) {
        return roleService.add(createRoleVO);
    }

    /**
     * 根据ID查询角色
     * @param id ID
     * @return 角色
     */
    @GetMapping("/findById/{id}")
    @RequestLog(desc = "根据ID查询角色")
    @SecurityMethod(antMatcher = "/api/role/findById/*", scope = "读取角色信息")
    public CommonResult<EditRoleVO> findById(@PathVariable(name = "id") Long id) {
        return roleService.findById(id);
    }

    /**
     * 分页查询角色
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    @PostMapping("/page")
    @RequestLog(desc = "分页查询角色")
    @SecurityMethod(antMatcher = "/api/role/page", scope = "读取角色信息", button = "ROLE_READ", buttonName = "搜索")
    public TableData<RoleVO> page(@RequestBody PageReqVO<QueryRoleVO> pageReqVO) {
        return roleService.page(pageReqVO);
    }

    /**
     * 修改角色
     * @param updateRoleVO 修改角色请求对象
     * @return 修改结果
     */
    @Validating
    @PostMapping("/update")
    @RequestLog(desc = "修改角色")
    @SecurityMethod(antMatcher = "/api/role/update", scope = "修改角色", button = "ROLE_UPDATE", buttonName = "编辑")
    public CommonResult<UpdateRoleVO> update(@RequestBody UpdateRoleVO updateRoleVO) {
        return roleService.update(updateRoleVO);
    }

    /**
     * 根据ID删除角色
     * @param id ID
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    @RequestLog(desc = "根据ID删除角色")
    @SecurityMethod(antMatcher = "/api/role/delete/*", scope = "删除角色", button = "ROLE_DELETE", buttonName = "删除")
    public CommonResult<Long> delete(@PathVariable(value = "id") Long id) {
        return roleService.delete(id);
    }

    /**
     * 根据角色名搜索角色
     * @param role 角色名称
     * @return 角色数据
     */
    @GetMapping("/search/{role}")
    @RequestLog(desc = "根据角色名搜索角色")
    @SecurityMethod(antMatcher = "/api/role/search/*", scope = "读取角色信息")
    public CommonResult<List<RoleVO>> search(@PathVariable(name = "role") String role) {
        return roleService.search(role);
    }
}
