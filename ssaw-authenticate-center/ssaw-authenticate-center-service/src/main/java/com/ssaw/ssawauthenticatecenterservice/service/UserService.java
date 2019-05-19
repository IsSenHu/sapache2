package com.ssaw.ssawauthenticatecenterservice.service;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.ShowUpdateUserVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserInfoVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserLoginVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.CreateUserVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.QueryUserVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UpdateUserVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HuSen.
 * @date 2018/12/11 9:58.
 */
public interface UserService extends UserDetailsService {
    /**
     * 分页查询用户
     * @param pageReq 分页查询请求参数
     * @return 分页结果
     */
    TableData<UserVO> page(PageReqVO<QueryUserVO> pageReq);

    /**
     * 新增用户
     * @param createUserVO 新增用户请求对象
     * @return 新增结果
     */
    CommonResult<CreateUserVO> add(CreateUserVO createUserVO);

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    CommonResult<ShowUpdateUserVO> findByUsername(String username);

    /**
     * 根据ID删除用户
     * @param id ID
     * @return 删除结果
     */
    CommonResult<Long> delete(Long id);

    /**
     * 修改用户
     * @param updateUserVO 修改用户请求对象
     * @return 修改结果
     */
    CommonResult<UpdateUserVO> update(UpdateUserVO updateUserVO);

    /**
     * 用户登录
     * @param userLoginVO 用户登录请求对象
     * @return 登录结果
     */
    CommonResult<UserInfoVO> login(UserLoginVO userLoginVO);


    /**
     * 根据ID查询用户
     * @param userId 用户ID
     * @return 用户
     */
    CommonResult<UserVO> findById(Long userId);

    /**
     * 用户登出
     * @param request HttpServletRequest
     * @return 登出结果
     */
    CommonResult<String> logout(HttpServletRequest request);
}
