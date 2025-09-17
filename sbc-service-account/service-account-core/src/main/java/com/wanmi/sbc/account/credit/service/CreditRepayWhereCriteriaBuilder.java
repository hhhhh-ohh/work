package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.CreditRepayPageRequest;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRepay;
import com.wanmi.sbc.common.enums.DeleteFlag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author houshuai
 * @date 2021/3/2 17:31
 * @description <p> 授信账号 还款查询条件</p>
 */
public class CreditRepayWhereCriteriaBuilder {

    public static Specification<CustomerCreditRepay> build(CreditRepayPageRequest request) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 是否当前周期
            if (ObjectUtils.allNotNull(request.getStartTime(), request.getEndTime())) {
                //查询不是已作废订单状态
                predicates.add(cbuild.notEqual(root.get("repayStatus"), CreditRepayStatus.VOID));
            }

            if (CollectionUtils.isNotEmpty(request.getRepayOrderCodeList())) {
                CriteriaBuilder.In<String> in = cbuild.in(root.get("repayOrderCode"));
                request.getRepayOrderCodeList().forEach(in::value);
                predicates.add(in);
            }

            predicates.add(cbuild.equal(root.get("customerId"), request.getCustomerId()));

            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));
            //排序
            cquery.orderBy(cbuild.desc(root.get("createTime")));
            Predicate[] p = predicates.toArray(new Predicate[]{});
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
