package com.ssaw.ssawmehelper.service.consumption;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawmehelper.dao.po.consumption.MyConsumptionPO;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionQueryVO;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionStatisticsVO;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionVO;

import java.util.List;

/**
 * @author HuSen
 * @date 2019/3/16 13:24
 */
public interface MyConsumptionService {

    /**
     * 批量保存我的收入
     * @param myConsumptionPOS 我的收入数据集合
     * @return 保存结果
     */
    CommonResult saveAll(List<MyConsumptionPO> myConsumptionPOS);

    /**
     * 分页查询我的消费
     * @param pageReqVO 分页查询数据模型
     * @return 分页结果
     */
    TableData<MyConsumptionVO> page(PageReqVO<MyConsumptionQueryVO> pageReqVO);

    /**
     * 获取我的消费折线图所需数据
     * @param start 开始时间
     * @param end 结束时间
     * @return 折线图所需数据
     */
    CommonResult<List<MyConsumptionStatisticsVO>> getMyConsumptionLineData(String start, String end);
}
