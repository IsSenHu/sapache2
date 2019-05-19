package com.ssaw.ssawauthenticatecenterservice.dao.entity.user;

import com.ssaw.supportjpa.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/02/28
 */
@Setter
@Getter
@Entity
@Table(name = "tb_user_role")
public class UserRoleEntity extends BaseEntity implements Serializable {

    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;
}
