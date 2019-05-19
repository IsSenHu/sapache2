package com.ssaw.ssawkafkademo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author HuSen
 * @date 2019/3/7 11:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateCatVO extends Log {
    private static final long serialVersionUID = 1663819397006757739L;

    private String name;
    private Integer age;
    private String sex;
    private String type;
    private String weight;
}