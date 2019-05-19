package com.ssaw.ssawauthenticatecenterservice.specification;

import com.ssaw.ssawauthenticatecenterfeign.vo.role.QueryRoleVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.role.RoleEntity;
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
 * @date 2018/12/14 19:43.
 */
public class RoleSpecification implements Specification<RoleEntity> {
    private static final long serialVersionUID = 2559617638686877846L;

    private QueryRoleVO queryRoleVO;

    public RoleSpecification(QueryRoleVO queryRoleVO) {
        this.queryRoleVO = queryRoleVO;
    }

    @Override
    public Predicate toPredicate(Root<RoleEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(queryRoleVO) && StringUtils.isNotBlank(queryRoleVO.getName())) {
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class), queryRoleVO.getName().concat("%")));
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
    }
}
