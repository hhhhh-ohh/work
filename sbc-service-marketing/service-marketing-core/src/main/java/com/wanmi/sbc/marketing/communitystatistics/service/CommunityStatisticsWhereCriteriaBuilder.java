package com.wanmi.sbc.marketing.communitystatistics.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsQueryRequest;
import com.wanmi.sbc.marketing.communitystatistics.model.root.CommunityStatistics;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区团购活动统计信息表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
public class CommunityStatisticsWhereCriteriaBuilder {
    public static Specification<CommunityStatistics> build(CommunityStatisticsQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // id
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 活动id
            if (StringUtils.isNotEmpty(queryRequest.getActivityId())) {
                predicates.add(cbuild.like(root.get("activityId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getActivityId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 模糊查询 - 团长id
            if (StringUtils.isNotEmpty(queryRequest.getLeaderId())) {
                predicates.add(cbuild.like(root.get("leaderId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLeaderId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 支付订单个数
            if (queryRequest.getPayNum() != null) {
                predicates.add(cbuild.equal(root.get("payNum"), queryRequest.getPayNum()));
            }

            // 支付总额
            if (queryRequest.getPayTotal() != null) {
                predicates.add(cbuild.equal(root.get("payTotal"), queryRequest.getPayTotal()));
            }

            // 帮卖团长数
            if (queryRequest.getAssistNum() != null) {
                predicates.add(cbuild.equal(root.get("assistNum"), queryRequest.getAssistNum()));
            }

            // 成团团长数
            if (queryRequest.getAssistPayNum() != null) {
                predicates.add(cbuild.equal(root.get("assistPayNum"), queryRequest.getAssistPayNum()));
            }

            // 帮卖订单数
            if (queryRequest.getAssistOrderNum() != null) {
                predicates.add(cbuild.equal(root.get("assistOrderNum"), queryRequest.getAssistOrderNum()));
            }

            // 帮卖总额
            if (queryRequest.getAssistOrderTotal() != null) {
                predicates.add(cbuild.equal(root.get("assistOrderTotal"), queryRequest.getAssistOrderTotal()));
            }

            // 帮卖占比
            if (queryRequest.getAssistOrderRatio() != null) {
                predicates.add(cbuild.equal(root.get("assistOrderRatio"), queryRequest.getAssistOrderRatio()));
            }

            // 退单数
            if (queryRequest.getReturnNum() != null) {
                predicates.add(cbuild.equal(root.get("returnNum"), queryRequest.getReturnNum()));
            }

            // 退单总数
            if (queryRequest.getReturnTotal() != null) {
                predicates.add(cbuild.equal(root.get("returnTotal"), queryRequest.getReturnTotal()));
            }

            // 帮卖退单数
            if (queryRequest.getAssistReturnNum() != null) {
                predicates.add(cbuild.equal(root.get("assistReturnNum"), queryRequest.getAssistReturnNum()));
            }

            // 帮卖退单总额
            if (queryRequest.getAssistReturnTotal() != null) {
                predicates.add(cbuild.equal(root.get("assistReturnTotal"), queryRequest.getAssistReturnTotal()));
            }

            // 已入账佣金
            if (queryRequest.getCommissionReceived() != null) {
                predicates.add(cbuild.equal(root.get("commissionReceived"), queryRequest.getCommissionReceived()));
            }

            // 已入账自提佣金
            if (queryRequest.getCommissionReceivedPickup() != null) {
                predicates.add(cbuild.equal(root.get("commissionReceivedPickup"), queryRequest.getCommissionReceivedPickup()));
            }

            // 已入账帮卖佣金
            if (queryRequest.getCommissionReceivedAssist() != null) {
                predicates.add(cbuild.equal(root.get("commissionReceivedAssist"), queryRequest.getCommissionReceivedAssist()));
            }

            // 未入账佣金
            if (queryRequest.getCommissionPending() != null) {
                predicates.add(cbuild.equal(root.get("commissionPending"), queryRequest.getCommissionPending()));
            }

            // 未入账自提佣金
            if (queryRequest.getCommissionPendingPickup() != null) {
                predicates.add(cbuild.equal(root.get("commissionPendingPickup"), queryRequest.getCommissionPendingPickup()));
            }

            // 未入账帮卖佣金
            if (queryRequest.getCommissionPendingAssist() != null) {
                predicates.add(cbuild.equal(root.get("commissionPendingAssist"), queryRequest.getCommissionPendingAssist()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
