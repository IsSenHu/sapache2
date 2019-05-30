package com.ssaw.config.server.api;

import com.ssaw.commons.annotations.RequestLog;
import com.ssaw.commons.annotations.Validating;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.config.sdk.vo.ConfigCreateVO;
import com.ssaw.config.sdk.vo.ConfigQueryVO;
import com.ssaw.config.sdk.vo.ConfigUpdateVO;
import com.ssaw.config.sdk.vo.ConfigViewVO;
import com.ssaw.config.server.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * @date 2019/5/28 15:37
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 创建配置文件
     *
     * @param createVO 创建配置文件数据模型
     * @return 创建结果
     */
    @Validating
    @PostMapping("/create")
    @RequestLog(desc = "创建配置文件")
    public CommonResult<ConfigCreateVO> create(@RequestBody ConfigCreateVO createVO) {
        return configService.create(createVO);
    }

    /**
     * 删除配置文件
     *
     * @param appId 应用ID
     * @return 删除结果
     */
    @Validating
    @GetMapping("/delete/{appId}")
    @RequestLog(desc = "删除配置文件")
    public CommonResult<String> delete(@PathVariable(name = "appId") String appId) {
        return configService.delete(appId);
    }

    /**
     * 分页查询配置
     *
     * @param pageReqVO 查询条件数据模型
     * @return 查询结果
     */
    @PostMapping("/page")
    @RequestLog(desc = "分页查询配置")
    public TableData<ConfigViewVO> page(@RequestBody PageReqVO<ConfigQueryVO> pageReqVO) {
        return configService.page(pageReqVO);
    }

    /**
     * 修改配置
     *
     * @param updateVO 修改配置数据模型
     * @return 修改结果
     */
    @PostMapping("/update")
    @Validating
    @RequestLog(desc = "修改配置")
    public CommonResult<ConfigUpdateVO> update(@RequestBody ConfigUpdateVO updateVO) {
        return configService.update(updateVO);
    }

    /**
     * 展示配置详情
     *
     * @param appId 应用ID
     * @return 配置详情
     */
    @GetMapping("/showConfig/{appId}")
    @RequestLog(desc = "展示配置详情")
    public CommonResult<String> showConfig(@PathVariable(name = "appId") String appId) {
        return configService.showConfig(appId);
    }
}