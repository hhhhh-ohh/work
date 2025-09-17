package com.wanmi.sbc.marketing.communityrel.utils;

import com.wanmi.sbc.marketing.api.request.communityrel.CommunityCommissionLeaderRelQueryRequest;
import com.wanmi.sbc.marketing.communityrel.model.root.CommunityCommissionLeaderRel;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区团购活动佣金团长关联表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-24 14:43:24
 */
public class CommunityCommissionLeaderRelWhereCriteriaBuilder {
    public static Specification<CommunityCommissionLeaderRel> build(CommunityCommissionLeaderRelQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 活动id
            if (StringUtils.isNotEmpty(queryRequest.getActivityId())) {
                predicates.add(cbuild.equal(root.get("activityId"), queryRequest.getActivityId()));
            }

            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getActivityIdList())) {
                predicates.add(root.get("activityId").in(queryRequest.getActivityIdList()));
            }

            // 团长Id
            if (StringUtils.isNotEmpty(queryRequest.getLeaderId())) {
                predicates.add(cbuild.equal(root.get("leaderId"), queryRequest.getLeaderId()));
            }

            // 团长自提点Id
            if (StringUtils.isNotEmpty(queryRequest.getPickupPointId())) {
                predicates.add(cbuild.equal(root.get("pickupPointId"), queryRequest.getPickupPointId()));
            }

            // 自提服务佣金
            if (queryRequest.getPickupCommission() != null) {
                predicates.add(cbuild.equal(root.get("pickupCommission"), queryRequest.getPickupCommission()));
            }

            // 帮卖团长佣金
            if (queryRequest.getAssistCommission() != null) {
                predicates.add(cbuild.equal(root.get("assistCommission"), queryRequest.getAssistCommission()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
