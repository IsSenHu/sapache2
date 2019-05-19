package com.ssaw.ssawmehelper.service.consumption.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssaw.commons.constant.Constants;
import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.util.time.DateUtil;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawmehelper.dao.mapper.consumption.MyConsumptionMapper;
import com.ssaw.ssawmehelper.dao.po.consumption.MyConsumptionPO;
import com.ssaw.ssawmehelper.model.constant.consumption.ConsumptionConstant;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionStatisticsVO;
import com.ssaw.ssawmehelper.service.consumption.BaseService;
import com.ssaw.ssawmehelper.service.consumption.MyConsumptionService;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionQueryVO;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;

/**
 * @author HuSen
 * @date 2019/3/16 13:24
 */
@Service
public class MyConsumptionServiceImpl extends BaseService implements MyConsumptionService {

    private final MyConsumptionMapper myConsumptionMapper;

    @Autowired
    public MyConsumptionServiceImpl(MyConsumptionMapper myConsumptionMapper) {
        this.myConsumptionMapper = myConsumptionMapper;
    }

    /**
     * 批量保存我的收入
     * @param myConsumptionPOS 我的收入数据集合
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult saveAll(List<MyConsumptionPO> myConsumptionPOS) {
        if (CollectionUtils.isEmpty(myConsumptionPOS)) {
            return CommonResult.createResult(Constants.ResultCodes.PARAM_ERROR, "导入数据为空", null);
        }
        Long userId = UserContextHolder.currentUser().getId();
        List<MyConsumptionPO> newPos = new ArrayList<>();
        for (MyConsumptionPO myConsumptionPO : myConsumptionPOS) {
            MyConsumptionPO po = myConsumptionMapper.findByCostDateAndUserId(myConsumptionPO.getCostDate(), userId);
            if (Objects.nonNull(po)) {
                po.setExpenditure(myConsumptionPO.getExpenditure());
                po.setIncome(myConsumptionPO.getIncome());
                po.setNetExpenditure(myConsumptionPO.getNetExpenditure());
                myConsumptionMapper.updateById(po);
            } else {
                newPos.add(myConsumptionPO);
            }
        }
        if (!CollectionUtils.isEmpty(newPos)) {
            myConsumptionMapper.saveAll(newPos);
        }
        return CommonResult.createResult(SUCCESS, "导入成功", null);
    }

    /**
     * 分页查询我的消费
     * @param pageReqVO 分页查询数据模型
     * @return 分页结果
     */
    @Override
    public TableData<MyConsumptionVO> page(PageReqVO<MyConsumptionQueryVO> pageReqVO) {
        pageReqVO = getPage(pageReqVO);
        IPage<MyConsumptionPO> iPage = new Page<MyConsumptionPO>()
                .setCurrent(pageReqVO.getPage()).setSize(pageReqVO.getSize()).setDesc("cost_date");
        iPage = myConsumptionMapper.findAll(iPage, pageReqVO.getData(), UserContextHolder.currentUser().getId());
        TableData<MyConsumptionVO> tableData = new TableData<>();
        tableData.setPage(pageReqVO.getPage());
        tableData.setSize(pageReqVO.getSize());
        tableData.setTotals(iPage.getTotal());
        tableData.setTotalPages((int) iPage.getPages());
        List<MyConsumptionVO> data = iPage.getRecords().stream().map(input -> CopyUtil.copyProperties(input, new MyConsumptionVO())).collect(Collectors.toList());
        tableData.setContent(data);
        return tableData;
    }

    /**
     * 获取我的消费折线图所需数据
     * @param start 开始时间
     * @param end 结束时间
     * @return 折线图所需数据
     */
    @Override
    public CommonResult<List<MyConsumptionStatisticsVO>> getMyConsumptionLineData(String start, String end) {
        Long id = UserContextHolder.currentUser().getId();
        List<MyConsumptionPO> pos = myConsumptionMapper.findAllByUserIdAndStartAndEnd(id, start, end);
        List<MyConsumptionStatisticsVO> result = new ArrayList<>();
        // 我的消费每日折线
        List<List<Object>> dailyLineData = new ArrayList<>();
        MyConsumptionStatisticsVO dailyLineDataStatisticsVO = new MyConsumptionStatisticsVO();
        dailyLineDataStatisticsVO.setName(ConsumptionConstant.MY_CONSUMPTION_DAILY_EXPENDITURE_LINE);
        // 我的收入每日折线
        List<List<Object>> dailyInComeLineData = new ArrayList<>();
        MyConsumptionStatisticsVO dailyIncomeLineDataStatisticsVO = new MyConsumptionStatisticsVO();
        dailyIncomeLineDataStatisticsVO.setName(ConsumptionConstant.MY_CONSUMPTION_DAILY_INCOME_LINE);
        // 我的净消费每日折线
        List<List<Object>> dailyNetExpenditureLineData = new ArrayList<>();
        MyConsumptionStatisticsVO dailyNetExpenditureLineDataStatisticsVO = new MyConsumptionStatisticsVO();
        dailyNetExpenditureLineDataStatisticsVO.setName(ConsumptionConstant.MY_CONSUMPTION_DAILY_NET_EXPENDITURE_LINE);
        for (MyConsumptionPO po : pos) {
            List<Object> dailyLinePointData = new ArrayList<>();
            dailyLinePointData.add(DateUtil.localDateFormat(po.getCostDate()));
            dailyLinePointData.add(po.getExpenditure());
            dailyLinePointData.add(1);
            dailyLineData.add(dailyLinePointData);

            List<Object> dailyIncomeLinePointData = new ArrayList<>();
            dailyIncomeLinePointData.add(DateUtil.localDateFormat(po.getCostDate()));
            dailyIncomeLinePointData.add(po.getIncome());
            dailyIncomeLinePointData.add(1);
            dailyInComeLineData.add(dailyIncomeLinePointData);

            List<Object> dailyNetExpenditureLinePointData = new ArrayList<>();
            dailyNetExpenditureLinePointData.add(DateUtil.localDateFormat(po.getCostDate()));
            dailyNetExpenditureLinePointData.add(po.getNetExpenditure());
            dailyNetExpenditureLinePointData.add(1);
            dailyNetExpenditureLineData.add(dailyNetExpenditureLinePointData);
        }
        dailyLineDataStatisticsVO.setData(dailyLineData);
        dailyIncomeLineDataStatisticsVO.setData(dailyInComeLineData);
        dailyNetExpenditureLineDataStatisticsVO.setData(dailyNetExpenditureLineData);
        result.add(dailyLineDataStatisticsVO);
        result.add(dailyIncomeLineDataStatisticsVO);
        result.add(dailyNetExpenditureLineDataStatisticsVO);
        return CommonResult.createResult(SUCCESS, "成功", result);
    }
}