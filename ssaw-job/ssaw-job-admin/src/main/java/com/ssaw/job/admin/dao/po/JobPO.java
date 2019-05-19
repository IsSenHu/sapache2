package com.ssaw.job.admin.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Job数据模型
 * @author HuSen
 * @date 2019/3/15 16:15
 */
@Data
@TableName("tb_job")
public class JobPO {

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** Job 名称 */
    @TableField("name")
    private String name;

    /** Job 所属的组名称 */
    @TableField("group")
    private String group;

    /** Job corn表达式 */
    @TableField("corn")
    private String corn;

    /** Job 执行自定义参数 */
    @TableField("param")
    private String param;
}