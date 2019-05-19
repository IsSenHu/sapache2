package com.ssaw.swaggerdemo.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * @date 2019/2/28 10:37
 */
@Setter
@Getter
@Entity
@Table(name = "tb_student")
public class StudentPO {

    /** 物理主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 逻辑主键
     * precision属性和scale属性表示精度，当字段类型为double时，precision表示数值的总长度，scale表示小数点所占的位数
     **/
    @Column(unique = true)
    private Long studentNo;

    /** 学生姓名 */
    @Column(nullable = false, length = 20)
    private String name;

    /** 生日 */
    @Column(nullable = false)
    private LocalDate birthday;

    /** 入学日期 */
    @Column(nullable = false)
    private LocalDate inDay;

    /**
     * ManyToOne:
     * targetEntity 定义关系类的类型, 默认是该成员属性对应的类类型, 所以通常不需要提供定义
     * optional 属性是定义该关联类是否必须存在, 值为false时, 关联类双方都必须存在, 如果关系被维护端不存在, 查询的结果为null.
     *      值为true 时, 关系被维护端可以不存在, 查询的结果仍然会返回关系维护端, 在关系维护端中指向关系被维护端的属性为null.
     *      optional属性的默认值是true, optional 属性实际上指定关联类与被关联类的join查询关系,
     *      如optional=false 时join 查询关系为inner join,
     *      optional=true 时join 查询关系为left join.
     * fetch 加载类型 (同时加载、懒加载)
     * cascade 该属性定义类和类之间的级联关系.
     *      定义的级联关系将被容器视为对当前类对象及其关联类对象采取相同的操作, 而且这种关系是递归调用的.
     *      CascadeType.PERSIST（级联新建）
     *      CascadeType.REMOVE（级联删除）    删除当前实体时，与它有映射关系的实体也会跟着被删除。
     *      CascadeType.REFRESH（级联刷新）
     *      CascadeType.MERGE（级联更新）    当Student中的数据改变，会相应地更新Class中的数据
     *      CascadeType.DETACH（级联脱管/游离操作）   如果你要删除一个实体，但是它有外键无法删除，你就需要这个级联权限了。它会撤销所有相关的外键关联.
     * JoinColumn:
     * name: 是默认将外实体的主键作为外键
     * referencedColumnName 则不是, 他可以定制
     * insertable、updatable 当使用JPA配置实体时, 如果有两个属性（一个是一般属性, 一个是多对一的属性）映射到数据库的同一列, 就会报错.
     *      例如有一个字段 class_id insertable = false, updatable = false 就能解决 不插入不更新到数据表
     * table 属性表示当映射多个表时，指定表的表中的字段
     * foreignKey 外键类型
     */
    @ManyToOne(targetEntity = ClassPO.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClassPO classPO;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 修改时间 */
    private LocalDateTime updateTime;
}