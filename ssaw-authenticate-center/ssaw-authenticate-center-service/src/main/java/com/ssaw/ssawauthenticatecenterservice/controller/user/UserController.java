package com.ssaw.ssawauthenticatecenterservice.controller.user;

import com.ssaw.commons.annotations.RequestLog;
import com.ssaw.commons.annotations.Validating;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.ShowUpdateUserVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserInfoVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserLoginVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.CreateUserVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.QueryUserVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UpdateUserVO;
import com.ssaw.ssawauthenticatecenterservice.controller.BaseController;
import com.ssaw.ssawauthenticatecenterservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HuSen.
 * @date 2018/12/14 20:12.
 */
@RestController
@RequestMapping("/api/user")
@SecurityApi(index = "2", group = "用户管理", menu = @Menu(index = "2-1", title = "用户", scope = "用户管理", to = "/authenticate/center/user"))
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(ApplicationContext context, UserService userService) {
        super(context);
        this.userService = userService;
    }

    /**
     * 分页查询用户
     * @param pageReq 分页查询请求参数
     * @return 分页结果
     */
    @RequestLog(desc = "分页查询用户")
    @PostMapping("/page")
    @SecurityMethod(antMatcher = "/api/user/page", scope = "读取用户信息", button = "USER_READ", buttonName = "搜索")
    public TableData<UserVO> page(@RequestBody PageReqVO<QueryUserVO> pageReq) {
        return userService.page(pageReq);
    }

    /**
     * 新增用户
     * @param createUserVO 新增用户请求对象
     * @return 新增结果
     */
    @Validating
    @PostMapping("/add")
    @RequestLog(desc = "新增用户")
    @SecurityMethod(antMatcher = "/api/user/add", scope = "创建用户", button = "USER_CREATE", buttonName = "编辑")
    public CommonResult<CreateUserVO> add(@RequestBody CreateUserVO createUserVO) {
        return userService.add(createUserVO);
    }

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    @PostMapping("/findByUsername/{username}")
    @RequestLog(desc = "通过用户名查询用户")
    @SecurityMethod(antMatcher = "/api/user/findByUsername/*", scope = "读取用户信息")
    public CommonResult<ShowUpdateUserVO> findByUsername(@PathVariable(value = "username") String username) {
        return userService.findByUsername(username);
    }

    /**
     * 根据ID删除用户
     * @param id ID
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    @RequestLog(desc = "根据ID删除用户")
    @SecurityMethod(antMatcher = "/api/user/delete/*", scope = "删除用户", button = "USER_DELETE", buttonName = "删除")
    public CommonResult<Long> delete(@PathVariable(value = "id") Long id) {
        return userService.delete(id);
    }

    /**
     * 修改用户
     * @param updateUserVO 修改用户请求对象
     * @return 修改结果
     */
    @Validating
    @PostMapping("/update")
    @RequestLog(desc = "修改用户")
    @SecurityMethod(antMatcher = "/api/user/update", scope = "更新用户", button = "USER_UPDATE", buttonName = "编辑")
    public CommonResult<UpdateUserVO> update(@RequestBody UpdateUserVO updateUserVO) {
        return userService.update(updateUserVO);
    }

    /**
     * 用户登录
     * @param userLoginVO 用户登录请求对象
     * @return 登录结果
     */
    @PostMapping("/login")
    @RequestLog(desc = "用户登录")
    public CommonResult<UserInfoVO> login(@RequestBody UserLoginVO userLoginVO) {
        return userService.login(userLoginVO);
    }

    /**
     * 用户登出
     * @param request HttpServletRequest
     * @return 登出结果
     */
    @PostMapping("/logout")
    @RequestLog(desc = "用户登出")
    public CommonResult<String> logout(HttpServletRequest request) {
        return userService.logout(request);
    }
}
