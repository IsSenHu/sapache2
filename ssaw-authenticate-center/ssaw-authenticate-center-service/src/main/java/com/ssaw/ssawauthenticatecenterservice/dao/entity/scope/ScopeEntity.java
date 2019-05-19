package com.ssaw.ssawauthenticatecenterservice.dao.entity.scope;

import com.ssaw.supportjpa.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/**
 * @author HuSen.
 * @date 2018/12/12 10:20.
 */
@Setter
@Getter
@Entity
@Table(name = "tb_scope")
public class ScopeEntity extends BaseEntity {
    @Column(unique = true)
    private String scope;

    @Column
    private String uri;

    @Column
    private Long resourceId;

    @Column
    private Long permissionId;
}
