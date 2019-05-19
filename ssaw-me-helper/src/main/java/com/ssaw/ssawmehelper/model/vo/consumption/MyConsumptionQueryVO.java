package com.ssaw.ssawmehelper.model.vo.consumption;

import lombok.Data;

import java.io.Serializable;

/**
 * 我的消费查询VO
 * @author HuSen
 * @date 2019/3/19 17:44
 */
@Data
public class MyConsumptionQueryVO implements Serializable {
    private static final long serialVersionUID = -7622905505587375943L;

    /** 开始时间 */
    private String start;
    /** 结束时间 */
    private String end;
}