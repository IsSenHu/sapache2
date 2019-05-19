package com.ssaw.ssawauthenticatecenterservice.dao.entity.permission;

import com.ssaw.supportjpa.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author HuSen.
 * @date 2018/12/13 16:47.
 */
@Setter
@Getter
@Entity
@Table(name = "tb_permission")
public class PermissionEntity extends BaseEntity {

    /** 权限名字 */
    private String name;

    /** 所关联作用域ID */
    private Long scopeId;

    /** 所关联资源ID */
    private Long resourceId;

    /** 描述 */
    private String description;
}
