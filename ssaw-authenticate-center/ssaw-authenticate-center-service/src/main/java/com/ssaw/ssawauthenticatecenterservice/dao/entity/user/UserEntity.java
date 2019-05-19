package com.ssaw.ssawauthenticatecenterservice.dao.entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author HuSen.
 * @date 2018/11/27 17:33.
 */
@Getter
@Setter
@Entity
@Table(name = "tb_user",
        indexes = {@Index(name = "index_is_enable", columnList = "is_enable")}
)
public class UserEntity {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户名 */
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    /** 用户密码 */
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    /** 是否可用 */
    @Column(name = "is_enable", length = 1)
    private Boolean isEnable;

    /** 真实姓名 */
    @Column(name = "real_name", length = 50)
    private String realName;

    /** 用户描述 */
    @Column(name = "description")
    private String description;

    /** 创建时间 */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /** 更新时间 */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /** 自定义信息 */
    @Column(name = "other_info", length = 5000)
    private String otherInfo;

    /** 是否内部系统用户 */
    @Column(name = "is_inner")
    private Boolean inner;
}
