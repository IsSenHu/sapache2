package com.ssaw.ssawauthenticatecenterfeign.feign;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawauthenticatecenterfeign.vo.*;
import com.ssaw.ssawauthenticatecenterfeign.fallback.AuthenticateFeignFallback;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserInfoVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserLoginVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * name: 指定FeignClient的名称，如果使用来Ribbon，name属性会作为微服务的名称，用于服务发现
 * decode404: 当发生404时，会调用decoder进行解码，否则抛出FeignException
 * fallback: 定义容错的类，fallback指定的类必须实现@FeignClient标记的接口
 * path: 定义当前FeignClient的统一前缀
 *
 * @author HuSen
 * @date 2019/02/14
 */
@Component
@FeignClient(name = "ssaw-authenticate-center", path = "/api", decode404 = true, fallback = AuthenticateFeignFallback.class)
public interface AuthenticateFeign {

    /**
     * 用户认证
     * @param requestUri 请求uri
     * @return 认证结果
     */
    @GetMapping("/authenticate")
    CommonResult<String> authenticate(@RequestParam(name = "requestUri") String requestUri);

    /**
     * 上传资源 白名单 作用域 菜单 按钮
     * @param uploadVO 上传信息数据模型
     * @return 上传结果
     */
    @PostMapping("/resource/uploadAuthenticateInfo")
    CommonResult<UploadVO> uploadAuthenticateInfo(@RequestBody UploadVO uploadVO);

    /**
     * 用户登录
     * @param userLoginVO 用户登录请求对象
     * @return 登录结果
     */
    @PostMapping("/user/login")
    CommonResult<UserInfoVO> login(@RequestBody UserLoginVO userLoginVO);
}
