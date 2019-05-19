package com.ssaw.ssawauthenticatecenterservice.specification;

import com.ssaw.ssawauthenticatecenterfeign.vo.scope.QueryScopeVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.scope.ScopeEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/13 14:55.
 */
public class ScopeSpecification implements Specification<ScopeEntity> {
    private static final long serialVersionUID = -8950553693828877396L;

    private QueryScopeVO queryScopeVO;

    public ScopeSpecification(QueryScopeVO queryScopeVO) {
        this.queryScopeVO = queryScopeVO;
    }

    @Override
    public Predicate toPredicate(Root<ScopeEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(StringUtils.isNotBlank(queryScopeVO.getScope())) {
            predicates.add(criteriaBuilder.like(root.get("scope").as(String.class), queryScopeVO.getScope() + "%"));
        }
        if(null != queryScopeVO.getResourceId()) {
            predicates.add(criteriaBuilder.equal(root.get("resourceId").as(Integer.class), queryScopeVO.getResourceId()));
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
    }
}
