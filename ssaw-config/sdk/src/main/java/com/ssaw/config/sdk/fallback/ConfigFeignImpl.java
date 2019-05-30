package com.ssaw.config.sdk.fallback;

import com.ssaw.commons.constant.Constants;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.config.sdk.feign.ConfigFeign;
import com.ssaw.config.sdk.vo.ConfigCreateVO;
import com.ssaw.config.sdk.vo.ConfigQueryVO;
import com.ssaw.config.sdk.vo.ConfigUpdateVO;
import com.ssaw.config.sdk.vo.ConfigViewVO;

/**
 * @author HuSen
 * @date 2019/5/30 16:57
 */
public class ConfigFeignImpl implements ConfigFeign {

    @Override
    public CommonResult<ConfigCreateVO> create(ConfigCreateVO createVO) {
        return CommonResult.createResult(Constants.ResultCodes.ERROR, "服务降级", createVO);
    }

    @Override
    public CommonResult<String> delete(String appId) {
        return CommonResult.createResult(Constants.ResultCodes.ERROR, "服务降级", appId);
    }

    @Override
    public TableData<ConfigViewVO> page(PageReqVO<ConfigQueryVO> pageReqVO) {
        return new TableData<>();
    }

    @Override
    public CommonResult<ConfigUpdateVO> update(ConfigUpdateVO updateVO) {
        return CommonResult.createResult(Constants.ResultCodes.ERROR, "服务降级", updateVO);
    }

    @Override
    public CommonResult<String> showConfig(String appId) {
        return CommonResult.createResult(Constants.ResultCodes.ERROR, "服务降级", appId);
    }
}