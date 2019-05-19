package com.ssaw.ssawauthenticatecenterservice.dao.entity.resource;

import com.ssaw.supportjpa.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/**
 * @author HuSen.
 * @date 2018/12/12 10:26.
 */
@Setter
@Getter
@Entity
@Table(name = "tb_resource")
public class ResourceEntity extends BaseEntity {
    private static final long serialVersionUID = 215116260012658207L;
    /**
     * 资源ID 和服务名相同
     */
    @Column(unique = true)
    private String resourceId;

    /**
     * 服务描述
     */
    @Column
    private String description;
}
