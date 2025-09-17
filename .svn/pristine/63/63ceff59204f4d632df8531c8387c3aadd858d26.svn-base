package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.RepayOrderPageRequest;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditOrder;
import com.wanmi.sbc.common.enums.DeleteFlag;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author houshuai
 * @date 2021/2/27 14:52
 * @description <p> 查询条件 </p>
 */
public class CreditOrderWhereCriteriaBuilder {

    public static Specification<CustomerCreditOrder> build(RepayOrderPageRequest request) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 是否当前周期
            if (Objects.nonNull(request.getIsCurrent())) {
                LocalDateTime nowTime = LocalDateTime.now();
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"), nowTime));
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("startTime"), nowTime));
            }
            // 是否使用
            if (Objects.nonNull(request.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), request.getCustomerId()));
            }
            // 还款单号
            if (Objects.nonNull(request.getRepayOrderCode())) {
                predicates.add(cbuild.equal(root.get("repayOrderCode"), request.getRepayOrderCode()));
            }

            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));
            Predicate[] p = predicates.toArray(new Predicate[]{});
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
