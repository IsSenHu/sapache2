package com.ssaw.swaggerdemo.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * @date 2019/2/28 11:30
 */
@Setter
@Getter
@Entity
@Table(name = "tb_class")
public class ClassPO {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** 班级名称 */
    @Column(nullable = false, length = 20)
    private String name;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 修改时间 */
    private LocalDateTime updateTime;
}