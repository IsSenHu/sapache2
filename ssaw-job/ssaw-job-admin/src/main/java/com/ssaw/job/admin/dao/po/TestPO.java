package com.ssaw.job.admin.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author HuSen
 * @date 2019/3/15 15:20
 */
@Data
@TableName("tb_test")
public class TestPO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("first_name")
    private String firstName;

    @TableField("last_name")
    private String lastName;
}