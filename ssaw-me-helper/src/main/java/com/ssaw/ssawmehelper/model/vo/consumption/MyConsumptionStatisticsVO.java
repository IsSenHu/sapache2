package com.ssaw.ssawmehelper.model.vo.consumption;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuSen
 * @date 2019/3/22 14:01
 */
@Data
public class MyConsumptionStatisticsVO implements Serializable {
    private static final long serialVersionUID = 7248586596661271201L;

    private String name;

    private List<List<Object>> data;
}