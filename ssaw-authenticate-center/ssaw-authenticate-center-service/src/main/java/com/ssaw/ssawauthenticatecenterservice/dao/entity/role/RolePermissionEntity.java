package com.ssaw.ssawauthenticatecenterservice.dao.entity.role;

import com.ssaw.supportjpa.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author HuSen.
 * @date 2018/12/14 20:13.
 */
@Setter
@Getter
@Entity
@Table(name = "tb_role_permission")
public class RolePermissionEntity extends BaseEntity {
    private static final long serialVersionUID = 7633446768363045816L;
    private Long roleId;
    private Long permissionId;
}
