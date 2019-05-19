package com.ssaw.ssawmehelper.model.vo.consumption;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 我的消费VO
 * @author HuSen
 * @date 2019/3/19 17:42
 */
@Data
public class MyConsumptionVO implements Serializable {
    private static final long serialVersionUID = 6260913675204937326L;

    /** 主键 */
    private Long id;

    /** 日期 */
    private LocalDate costDate;

    /** 支出 */
    private BigDecimal expenditure;

    /** 收入 */
    private BigDecimal income;

    /** 净支出 */
    private BigDecimal netExpenditure;
}