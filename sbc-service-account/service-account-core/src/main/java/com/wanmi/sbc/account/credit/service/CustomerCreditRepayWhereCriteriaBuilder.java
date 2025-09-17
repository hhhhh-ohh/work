package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayQueryRequest;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRepay;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>客户授信还款动态查询条件构建器</p>
 * @author chenli
 * @date 2020-03-24 16:21:28
 */
public class CustomerCreditRepayWhereCriteriaBuilder {
    public static Specification<CustomerCreditRepay> build(CustomerCreditRepayQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>(20);
            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 客户id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), queryRequest.getCustomerId()));
            }

            // 模糊查询 - 还款单号
            if (StringUtils.isNotEmpty(queryRequest.getRepayOrderCode())) {
                predicates.add(cbuild.like(root.get("repayOrderCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getRepayOrderCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 授信额度
            if (queryRequest.getCreditAmount() != null) {
                predicates.add(cbuild.equal(root.get("creditAmount"), queryRequest.getCreditAmount()));
            }

            // 还款金额
            if (queryRequest.getRepayAmount() != null) {
                predicates.add(cbuild.equal(root.get("repayAmount"), queryRequest.getRepayAmount()));
            }

            // 模糊查询 - 还款说明
            if (StringUtils.isNotEmpty(queryRequest.getRepayNotes())) {
                predicates.add(cbuild.like(root.get("repayNotes"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getRepayNotes()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 还款状态 0待还款 1还款成功 2 已作废
            if (queryRequest.getRepayStatus() != null) {
                predicates.add(cbuild.equal(root.get("repayStatus"), queryRequest.getRepayStatus()));
            }

            // 还款方式 0银联，1微信，2支付宝
            if (queryRequest.getRepayType() != null) {
                predicates.add(cbuild.equal(root.get("repayType"), queryRequest.getRepayType()));
            }

            // 大于或等于 搜索条件:还款时间开始
            if (queryRequest.getRepayTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("repayTime"),
                        queryRequest.getRepayTimeBegin()));
            }
            // 小于或等于 搜索条件:还款时间截止
            if (queryRequest.getRepayTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("repayTime"),
                        queryRequest.getRepayTimeEnd()));
            }

            // 大于或等于 搜索条件:创建时间开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:创建时间截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeEnd()));
            }

            // 模糊查询 - 创建人id
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(cbuild.like(root.get("createPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCreatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:更新时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:更新时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            // 模糊查询 - 更新人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否删除标志 0：否，1：是
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 批量查询-客户idList
            if (CollectionUtils.isNotEmpty(queryRequest.getCustomerIdList())) {
                predicates.add(root.get("customerId").in(queryRequest.getCustomerIdList()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
