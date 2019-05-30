package com.ssaw.config.server.service;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.config.sdk.vo.ConfigCreateVO;
import com.ssaw.config.sdk.vo.ConfigQueryVO;
import com.ssaw.config.sdk.vo.ConfigUpdateVO;
import com.ssaw.config.sdk.vo.ConfigViewVO;

/**
 * @author HuSen
 * @date 2019/5/28 15:44
 */
public interface ConfigService {

    /**
     * 创建配置文件
     *
     * @param createVO 创建配置文件数据模型
     * @return 创建结果
     */
    CommonResult<ConfigCreateVO> create(ConfigCreateVO createVO);

    /**
     * 删除配置文件
     *
     * @param appId 应用ID
     * @return 删除结果
     */
    CommonResult<String> delete(String appId);

    /**
     * 分页查询配置
     *
     * @param pageReqVO 查询条件数据模型
     * @return 查询结果
     */
    TableData<ConfigViewVO> page(PageReqVO<ConfigQueryVO> pageReqVO);

    /**
     * 修改配置
     *
     * @param updateVO 修改配置数据模型
     * @return 修改结果
     */
    CommonResult<ConfigUpdateVO> update(ConfigUpdateVO updateVO);

    /**
     * 展示配置详情
     *
     * @param appId 应用ID
     * @return 配置详情
     */
    CommonResult<String> showConfig(String appId);
}
