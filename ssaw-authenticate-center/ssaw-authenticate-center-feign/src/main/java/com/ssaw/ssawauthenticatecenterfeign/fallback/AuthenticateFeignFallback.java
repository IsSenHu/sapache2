package com.ssaw.ssawauthenticatecenterfeign.fallback;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawauthenticatecenterfeign.vo.*;
import com.ssaw.ssawauthenticatecenterfeign.feign.AuthenticateFeign;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserInfoVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserLoginVO;

import static com.ssaw.commons.constant.Constants.ResultCodes.ERROR;

/**
 * @author HuSen
 * @date 2019/02/14
 */
public class AuthenticateFeignFallback implements AuthenticateFeign {

    @Override
    public CommonResult<String> authenticate(String requestUri) {
        return CommonResult.createResult(ERROR, "服务降级", null);
    }

    @Override
    public CommonResult<UploadVO> uploadAuthenticateInfo(UploadVO uploadVO) {
        return CommonResult.createResult(ERROR, "服务降级", null);
    }

    @Override
    public CommonResult<UserInfoVO> login(UserLoginVO userLoginVO) {
        return CommonResult.createResult(ERROR, "服务降级", null);
    }
}
