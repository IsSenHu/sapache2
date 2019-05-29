package com.ssaw.ssawconfig.service.impl;

import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawconfig.dao.mo.ConfigPO;
import com.ssaw.ssawconfig.dao.repository.ConfigRepository;
import com.ssaw.ssawconfig.model.vo.*;
import com.ssaw.ssawconfig.service.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssaw.commons.constant.Constants.ResultCodes.*;

/**
 * @author HuSen
 * @date 2019/5/28 15:44
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ConfigServiceImpl(ConfigRepository configRepository, MongoTemplate mongoTemplate) {
        this.configRepository = configRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 创建配置文件
     *
     * @param createVO 创建配置文件数据模型
     * @return 创建结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<ConfigCreateVO> create(ConfigCreateVO createVO) {
        long count = configRepository.countAllByApplicationAndProfileAndLabel(createVO.getApplication(), createVO.getProfile(), createVO.getLabel());
        if (count > 0) {
            return CommonResult.createResult(PARAM_ERROR, "该应用配置已存在", createVO);
        }
        String username = UserContextHolder.currentUser().getUsername();
        ConfigPO configPO = CopyUtil.copyProperties(createVO, new ConfigPO());
        configPO.setConfig(createVO.getText());
        configPO.setCreateMan(username);
        configPO.setModifyMan(username);
        configPO.setCreateTime(LocalDateTime.now());
        configPO.setModifyTime(LocalDateTime.now());
        configRepository.save(configPO);
        return CommonResult.createResult(SUCCESS, "成功", createVO);
    }

    /**
     * 删除配置文件
     *
     * @param appId 应用ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> delete(@RequestBody String appId) {
        configRepository.deleteById(appId);
        return CommonResult.createResult(SUCCESS, "成功", appId);
    }

    /**
     * 分页查询配置
     *
     * @param pageReqVO 查询条件数据模型
     * @return 查询结果
     */
    @Override
    public TableData<ConfigViewVO> page(PageReqVO<ConfigQueryVO> pageReqVO) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        ConfigQueryVO queryVO = pageReqVO.getData();
        if (StringUtils.isNotBlank(queryVO.getApplication())) {
            criteria.and("application").is(queryVO.getApplication());
        }
        if (StringUtils.isNotBlank(queryVO.getLabel())) {
            criteria.and("label").is(queryVO.getLabel());
        }
        if (StringUtils.isNotBlank(queryVO.getProfile())) {
            criteria.and("profile").is(queryVO.getProfile());
        }
        query.addCriteria(criteria);
        Pageable pageable = PageRequest.of(pageReqVO.getPage() - 1, pageReqVO.getSize());
        List<ConfigPO> content = mongoTemplate.find(query.with(pageable), ConfigPO.class);
        TableData<ConfigViewVO> result = new TableData<>();
        result.setPage(pageReqVO.getPage());
        result.setSize(pageReqVO.getSize());
        long totals = mongoTemplate.count(query, ConfigPO.class);
        result.setTotalPages((int) (totals % pageReqVO.getSize() == 0 ? totals / pageReqVO.getSize() : totals / pageReqVO.getSize() + 1));
        result.setTotals(totals);
        result.setContent(content.stream().map(input -> CopyUtil.copyProperties(input, new ConfigViewVO())).collect(Collectors.toList()));
        return result;
    }

    /**
     * 修改配置
     *
     * @param updateVO 修改配置数据模型
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<ConfigUpdateVO> update(ConfigUpdateVO updateVO) {
        Optional<ConfigPO> byId = configRepository.findById(updateVO.getAppId());
        if (!byId.isPresent()) {
            return CommonResult.createResult(PARAM_ERROR, "没有该应用", updateVO);
        }
        ConfigPO configPO = byId.get();
        configPO.setModifyTime(LocalDateTime.now());
        configPO.setModifyMan(UserContextHolder.currentUser().getUsername());
        configPO.setConfig(updateVO.getText());
        configRepository.save(configPO);
        return CommonResult.createResult(SUCCESS, "成功", updateVO);
    }

    /**
     * 展示配置详情 .map.....orElseGet....
     *
     * @param appId 应用ID
     * @return 配置详情
     */
    @Override
    public CommonResult<String> showConfig(String appId) {
        return configRepository.findById(appId)
                .map(configPO -> CommonResult.createResult(SUCCESS, "成功", configPO.getConfig()))
                .orElseGet(() -> CommonResult.createResult(PARAM_ERROR, "应用不存在", appId));
    }
}