package com.ssaw.ssawauthenticatecenterservice.specification;

import com.ssaw.ssawauthenticatecenterfeign.vo.user.QueryUserVO;
import com.ssaw.ssawauthenticatecenterservice.dao.entity.user.UserEntity;
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
 * @date 2019/1/4 15:45.
 */
public class UserSpecification implements Specification<UserEntity> {
    private static final long serialVersionUID = 60731481180465950L;

    private QueryUserVO queryUserVO;

    public UserSpecification(QueryUserVO queryUserVO) {
        this.queryUserVO = queryUserVO;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>(2);
        if(null != queryUserVO) {
            if(null != queryUserVO.getId()) {
                predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), queryUserVO.getId()));
            }
            if(StringUtils.isNotBlank(queryUserVO.getUsername())) {
                predicates.add(criteriaBuilder.like(root.get("username").as(String.class), queryUserVO.getUsername().concat("%")));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
    }
}
