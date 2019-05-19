package com.ssaw.ssawauthenticatecenterservice.controller;

import com.ssaw.commons.exceptions.ParamException;
import com.ssaw.commons.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

import static com.ssaw.commons.constant.Constants.ResultCodes.*;

/**
 * @author HuSen.
 * @date 2018/11/27 19:00.
 */
@Slf4j
@RestController
public class BaseController {

    public BaseController(ApplicationContext context) {}

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 全局统一异常处理
     * @param request HttpServletRequest请求对象
     * @param e 异常
     * @return 异常响应
     */
    @ExceptionHandler
    public CommonResult<Object> exceptionHandle(HttpServletRequest request, Exception e) {
        log.info("请求接口地址:[{}]，发生异常:", request.getRequestURI(), e);
        CommonResult<Object> commonResult = new CommonResult<>();
        commonResult.setTs(System.currentTimeMillis());
        if(e instanceof ParamException) {
            commonResult.setCode(PARAM_ERROR);
            commonResult.setMessage("参数错误!");
            ParamException paramException = (ParamException) e;
            commonResult.setData(paramException.getErrors());
            return commonResult;
        }else {
            commonResult.setCode(ERROR);
            commonResult.setMessage("服务器内部错误!");
            commonResult.setData(e.getMessage());
            return commonResult;
        }
    }

    /**
     * 获取认证中心服名称
     * @return 服务名称
     */
    @GetMapping("/api/base/getApplicationName")
    public CommonResult<String> getApplicationName() {
        return CommonResult.createResult(SUCCESS, "成功", applicationName);
    }
}
