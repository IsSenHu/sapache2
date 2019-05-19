package com.ssaw.ssawauthenticatecenterservice.specification;

import com.ssaw.ssawauthenticatecenterfeign.vo.client.QueryClientVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.client.ClientDetailsEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hszyp
 * @date 2019/01/27
 */
public class ClientSpecification implements Specification<ClientDetailsEntity> {
    private static final long serialVersionUID = 1383260135547038273L;

    private QueryClientVO queryClientVO;

    public ClientSpecification(QueryClientVO queryClientVO) {
        this.queryClientVO = queryClientVO;
    }

    @Override
    public Predicate toPredicate(Root<ClientDetailsEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(null != queryClientVO && StringUtils.isNotBlank(queryClientVO.getClientId())) {
            predicates.add(criteriaBuilder.like(root.get("clientId").as(String.class), queryClientVO.getClientId().concat("%")));
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
    }
}
