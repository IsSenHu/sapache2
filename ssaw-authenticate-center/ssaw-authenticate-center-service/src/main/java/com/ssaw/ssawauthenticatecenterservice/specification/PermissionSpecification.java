package com.ssaw.ssawauthenticatecenterservice.specification;

import com.ssaw.ssawauthenticatecenterfeign.vo.permission.QueryPermissionVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.permission.PermissionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author HuSen.
 * @date 2018/12/12 17:00.
 */
public class PermissionSpecification implements Specification<PermissionEntity> {
    private static final long serialVersionUID = -8184562005093817578L;

    private QueryPermissionVO queryPermissionVO;

    public PermissionSpecification(QueryPermissionVO queryPermissionVO) {
        this.queryPermissionVO = queryPermissionVO;
    }

    @Override
    public Predicate toPredicate(Root<PermissionEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(StringUtils.isNotBlank(queryPermissionVO.getName())) {
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class), queryPermissionVO.getName() + "%"));
        }
        if(!Objects.isNull(queryPermissionVO.getResourceId())) {
            predicates.add(criteriaBuilder.equal(root.get("resourceId").as(Long.class), queryPermissionVO.getResourceId()));
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
    }
}
