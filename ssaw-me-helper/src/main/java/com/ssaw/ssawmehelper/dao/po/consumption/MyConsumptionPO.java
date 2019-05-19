package com.ssaw.ssawmehelper.dao.po.consumption;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 我的消费数据模型
 * @author HuSen
 * @date 2019/3/16 12:15
 */
@Data
@TableName("tb_my_consumption")
public class MyConsumptionPO {
    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 日期 */
    @TableField("cost_date")
    private LocalDate costDate;

    /** 支出 */
    @TableField("expenditure")
    private BigDecimal expenditure;

    /** 收入 */
    @TableField("income")
    private BigDecimal income;

    /** 净支出 */
    @TableField("net_expenditure")
    private BigDecimal netExpenditure;

    /** 消费记录的用户 */
    @TableField("user_id")
    private Long userId;
}