package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.CreditRepayOverviewPageRequest;
import com.wanmi.sbc.account.credit.model.root.CustomerApplyRecord;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRepay;
import com.wanmi.sbc.common.enums.DeleteFlag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author houshuai
 * @date 2021/3/2 17:31
 * @description <p> 授信账号 还款查询条件</p>
 */
public class CreditRepayListWhereCriteriaBuilder {

    public static Specification<CustomerCreditRepay> build(CreditRepayOverviewPageRequest request) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 是否当前周期
            Join<CustomerCreditAccount, CustomerApplyRecord> accountJoin = root.join("customerCreditAccount");
            // 模糊查询 - 会员名称
            if (StringUtils.isNotBlank(request.getCustomerName())) {
                predicates.add(cbuild.like(accountJoin.get("customerName"), "%" + request.getCustomerName() + "%"));
            }

            // 模糊查询 - 会员账号
            if (StringUtils.isNotBlank(request.getCustomerAccount())) {
                predicates.add(cbuild.like(accountJoin.get("customerAccount"), "%" + request.getCustomerAccount() + "%"));
            }

            // 还款单号
            if (StringUtils.isNotBlank(request.getRepayOrderCode())) {
                predicates.add( cbuild.like(root.get("repayOrderCode"), "%" + request.getRepayOrderCode() + "%"));
            }

            // 批量查询-业务员关联的会员IDList
            if (CollectionUtils.isNotEmpty(request.getEmployeeCustomerIds())) {
                predicates.add(root.get("customerId").in(request.getEmployeeCustomerIds()));
            }

            // 还款状态
            if (Objects.nonNull(request.getRepayStatus())) {
                predicates.add(cbuild.equal(root.get("repayStatus"), request.getRepayStatus()));
            }
            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));
            //排序
            cquery.orderBy(cbuild.desc(root.get("createTime")));
            Predicate[] p = predicates.toArray(new Predicate[]{});
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
