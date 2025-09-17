package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.account.credit.model.root.CustomerApplyRecord;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author houshuai
 * @date 2021/4/6 14:25
 * @description <p> 额度恢复查询 </p>
 */
public class CreditRecoverWhereCriteriaBuilder {

    public static Specification<CustomerCreditAccount> build(CreditAccountPageRequest request) {

        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<CustomerCreditAccount, CustomerApplyRecord> applyJoin = root.join("customerApplyRecord");

            //取额度可恢复账户
            if (Objects.nonNull(request.getRepayAmount()) && request.getRepayAmount().compareTo(BigDecimal.ZERO) == NumberUtils.INTEGER_ZERO) {
                predicates.add(cbuild.equal(root.get("repayAmount"), BigDecimal.ZERO));
            }

            //取额度可恢复账户
            if (Objects.nonNull(request.getNowTime())) {
                predicates.add(cbuild.lessThan(root.get("endTime"), request.getNowTime()));

            }
            //已启用
            Predicate pass = cbuild.equal(root.get("enabled"), BoolFlag.YES);
            //额度变更中
            Predicate resetWait = cbuild.equal(root.get("enabled"), BoolFlag.NO);
            Predicate changeRecordId = cbuild.isNotNull(root.get("changeRecordId"));
            Predicate predicate = cbuild.and(resetWait, changeRecordId);

            predicates.add(cbuild.or(pass,predicate));

            //授信账户----审核通过
            CriteriaBuilder.In<Object> auditStatus = cbuild.in(applyJoin.get("auditStatus"));
            auditStatus.value(CreditAuditStatus.PASS);
            //额度变更数据
            auditStatus.value(CreditAuditStatus.RESET_WAIT);
            predicates.add(auditStatus);
            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));

            Predicate[] p = predicates.toArray(new Predicate[]{});
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
