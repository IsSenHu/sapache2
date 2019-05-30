package com.ssaw.config.sdk.feign;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.config.sdk.fallback.ConfigFeignImpl;
import com.ssaw.config.sdk.vo.ConfigCreateVO;
import com.ssaw.config.sdk.vo.ConfigQueryVO;
import com.ssaw.config.sdk.vo.ConfigUpdateVO;
import com.ssaw.config.sdk.vo.ConfigViewVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * @date 2019/5/30 16:55
 */
@FeignClient(name = "config-server", path = "/api/config", decode404 = true, fallback = ConfigFeignImpl.class)
public interface ConfigFeign {

    /**
     * 创建配置文件
     *
     * @param createVO 创建配置文件数据模型
     * @return 创建结果
     */
    @PostMapping(value = "/create")
    CommonResult<ConfigCreateVO> create(@RequestBody ConfigCreateVO createVO);

    /**
     * 删除配置文件
     *
     * @param appId 应用ID
     * @return 删除结果
     */
    @GetMapping("/delete/{appId}")
    CommonResult<String> delete(@PathVariable(name = "appId") String appId);

    /**
     * 分页查询配置
     *
     * @param pageReqVO 查询条件数据模型
     * @return 查询结果
     */
    @PostMapping("/page")
    TableData<ConfigViewVO> page(@RequestBody PageReqVO<ConfigQueryVO> pageReqVO);

    /**
     * 修改配置
     *
     * @param updateVO 修改配置数据模型
     * @return 修改结果
     */
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    CommonResult<ConfigUpdateVO> update(@RequestBody ConfigUpdateVO updateVO);

    /**
     * 展示配置详情
     *
     * @param appId 应用ID
     * @return 配置详情
     */
    @GetMapping("/showConfig/{appId}")
    CommonResult<String> showConfig(@PathVariable(name = "appId") String appId);
}
