package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.account.credit.model.root.CustomerApplyRecord;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author houshuai
 * @date 2021/2/27 14:52
 * @description <p> 查询条件 </p>
 */
public class CreditAccountWhereCriteriaBuilder {

    public static Specification<CustomerCreditAccount> build(CreditAccountPageRequest request) {

        return (root, cquery, cbuild) -> {
            LocalDateTime nowTime = LocalDateTime.now();
            List<Predicate> predicates = new ArrayList<>();

            Join<CustomerCreditAccount, CustomerApplyRecord> applyJoin = root.join("customerApplyRecord");

            //Join<CustomerCreditAccount, CustomerCreditRecord> creditJoin = root.join("customerCreditRecord", JoinType.LEFT);

            // 模糊查询 - 会员名称
            if (StringUtils.isNotBlank(request.getCustomerName())) {
                predicates.add(cbuild.like(root.get("customerName"), "%" + request.getCustomerName() + "%"));
            }

            // 模糊查询 - 会员账号
            if (StringUtils.isNotBlank(request.getCustomerAccount())) {
                predicates.add(cbuild.like(root.get("customerAccount"), "%" + request.getCustomerAccount() + "%"));
            }

            // 是否使用
            if (Objects.nonNull(request.getUsedStatus())) {
                predicates.add(cbuild.equal(root.get("usedStatus"), request.getUsedStatus()));
            }

            // customerId
            if (StringUtils.isNotBlank(request.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), request.getCustomerId()));
            }

            // 未过期
            if (Objects.nonNull(request.getExpireStatus()) && Objects.equals(request.getExpireStatus(), BoolFlag.NO)) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), nowTime));
            }

            // 已过期
            if (Objects.nonNull(request.getExpireStatus()) && Objects.equals(request.getExpireStatus(), BoolFlag.YES)) {
                predicates.add(cbuild.lessThan(root.get("endTime"), nowTime));
            }

            //取额度可恢复账户
            if (Objects.nonNull(request.getRepayAmount()) && request.getRepayAmount().compareTo(BigDecimal.ZERO) == 0) {
                predicates.add(cbuild.equal(root.get("repayAmount"), BigDecimal.ZERO));
            }

            //取额度可恢复账户
            if (Objects.nonNull(request.getNowTime())) {
                predicates.add(cbuild.lessThan(root.get("endTime"), request.getNowTime()));
            }

            // 等于当天
            if (Objects.nonNull(request.getEndTime())) {
                LocalDateTime endTime = request.getEndTime();
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), endTime.with(LocalTime.of(00, 00, 00))));
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"), endTime.with(LocalTime.of(23, 59, 59))));
            }

            // 批量查询-业务员关联的会员IDList
            if (CollectionUtils.isNotEmpty(request.getEmployeeCustomerIds())) {
                predicates.add(root.get("customerId").in(request.getEmployeeCustomerIds()));
            }

            //是否是可用状态
            if (Objects.nonNull(request.getEnabled())) {
                predicates.add(cbuild.equal(root.get("enabled"), request.getEnabled()));
            }


            //授信账户----审核通过
            predicates.add(cbuild.equal(applyJoin.get("auditStatus"), CreditAuditStatus.PASS));
            /*CriteriaBuilder.In<Object> auditStatus = cbuild.in(applyJoin.get("auditStatus"));
            auditStatus.value(CreditAuditStatus.PASS);
            //额度变更数据
            auditStatus.value(CreditAuditStatus.RESET_WAIT);
            predicates.add(auditStatus);*/

            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));

            Predicate[] p = predicates.toArray(new Predicate[]{});
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
