package com.ssaw.ssawauthenticatecenterservice.controller.permission;

import com.ssaw.commons.annotations.RequestLog;
import com.ssaw.commons.annotations.Validating;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawauthenticatecenterfeign.vo.permission.CreatePermissionVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.permission.PermissionVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.permission.QueryPermissionVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.permission.UpdatePermissionVO;
import com.ssaw.ssawauthenticatecenterservice.controller.BaseController;
import com.ssaw.ssawauthenticatecenterservice.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen.
 * @date 2018/12/13 16:58.
 */
@RestController
@RequestMapping("/api/permission")
@SecurityApi(index = "2", group = "用户管理", menu = @Menu(index = "2-3", title = "权限", scope = "权限管理", to = "/authenticate/center/permission"))
public class PermissionController extends BaseController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(ApplicationContext context, PermissionService permissionService) {
        super(context);
        this.permissionService = permissionService;
    }

    /**
     * 新增权限
     * @param createPermissionVO 新增权限请求对象
     * @return 新增结果
     */
    @Validating
    @PostMapping("/add")
    @RequestLog(desc = "新增权限")
    @SecurityMethod(antMatcher = "/api/permission/add", scope = "创建权限", button = "PERMISSION_CREATE", buttonName = "添加")
    public CommonResult<CreatePermissionVO> add(@RequestBody CreatePermissionVO createPermissionVO) {
        return permissionService.add(createPermissionVO);
    }

    /**
     * 根据ID查询权限
     * @param id ID
     * @return 权限
     */
    @GetMapping("/findById/{id}")
    @RequestLog(desc = "根据ID查询权限")
    @SecurityMethod(antMatcher = "/api/permission/findById/*", scope = "读取权限信息")
    public CommonResult<PermissionVO> findById(@PathVariable(name = "id") Long id) {
        return permissionService.findById(id);
    }

    /**
     * 分页查询权限
     * @param pageReqVO 分页查询参数
     * @return 分页结果
     */
    @PostMapping("/page")
    @RequestLog(desc = "分页查询权限")
    @SecurityMethod(antMatcher = "/api/permission/page", scope = "读取权限信息", button = "PERMISSION_READ", buttonName = "搜索")
    public TableData<PermissionVO> page(@RequestBody PageReqVO<QueryPermissionVO> pageReqVO) {
        return permissionService.page(pageReqVO);
    }

    /**
     * 根据ID删除权限
     * @param id ID
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    @RequestLog(desc = "根据ID删除权限")
    @SecurityMethod(antMatcher = "/api/permission/delete/*", scope = "删除权限", button = "PERMISSION_DELETE", buttonName = "删除")
    public CommonResult<Long> delete(@PathVariable(name = "id") Long id) {
        return permissionService.delete(id);
    }

    /**
     * 修改权限
     * @param updatePermissionVO 修改请求对象
     * @return 修改结果
     */
    @Validating
    @PostMapping("/update")
    @RequestLog(desc = "修改权限")
    @SecurityMethod(antMatcher = "/api/permission/update", scope = "修改权限", button = "PERMISSION_UPDATE", buttonName = "编辑")
    public CommonResult<UpdatePermissionVO> update(@RequestBody UpdatePermissionVO updatePermissionVO) {
        return permissionService.update(updatePermissionVO);
    }
}
