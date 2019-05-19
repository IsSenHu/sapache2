package com.ssaw.ssawmehelper.dao.po.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author HuSen
 * @date 2019/4/30 10:57
 */
@Data
@TableName("tb_wf")
public class WfPO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("spbm")
    private String spbm;
    @TableField("sign_ids")
    private String signIds;
    @TableField("ehr_bn")
    private String ehrBn;
    @TableField("ehr_ap")
    private String ehrAp;
    @TableField("success")
    private Boolean success;
}