package com.ssaw.ssawauthenticatecenterservice.dao.entity.role;

import com.ssaw.supportjpa.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author HuSen.
 * @date 2018/12/14 17:24.
 */
@Setter
@Getter
@Entity
@Table(name = "tb_role")
public class RoleEntity extends BaseEntity {

    @Column(unique = true)
    private String name;

    @Column
    private String description;
}
