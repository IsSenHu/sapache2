package com.ssaw.ssawmehelper.dao.mapper.consumption;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssaw.ssawmehelper.dao.po.consumption.MyConsumptionPO;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionQueryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author HuSen
 * @date 2019/3/16 12:20
 */
@Repository
public interface MyConsumptionMapper extends BaseMapper<MyConsumptionPO> {

    /**
     * 批量保存我的收入
     * @param myConsumptionPOS 我的收入数据集合
     */
    void saveAll(List<MyConsumptionPO> myConsumptionPOS);

    /**
     * 根据消费日期查询我的消费记录
     * @param costDate 消费日期
     * @param userId 用户ID
     * @return 我的消费记录
     */
    MyConsumptionPO findByCostDateAndUserId(@Param("costDate") LocalDate costDate, @Param("userId") Long userId);

    /**
     * 分页查询我的消费
     * @param page 分页数据模型
     * @param data 查询条件数据模型
     * @param userId 用户ID
     * @return 分页结果
     */
    Page<MyConsumptionPO> findAll(IPage<MyConsumptionPO> page, @Param("query") MyConsumptionQueryVO data, @Param("userId") Long userId);

    /**
     * 根据用户名，开始时间，结束时间查询我的消费
     * @param userId 用户ID
     * @param start 开始时间
     * @param end 结束时间
     * @return 我的消费
     */
    List<MyConsumptionPO> findAllByUserIdAndStartAndEnd(@Param("userId") Long userId, @Param("start") String start, @Param("end") String end);
}
