package com.wanmi.sbc.marketing.communitysku.service;

import com.wanmi.sbc.marketing.api.request.communitysku.CommunitySkuRelQueryRequest;
import com.wanmi.sbc.marketing.communitysku.model.root.CommunitySkuRel;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区团购活动商品表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-24 14:47:53
 */
public class CommunitySkuRelWhereCriteriaBuilder {
    public static Specification<CommunitySkuRel> build(CommunitySkuRelQueryRequest queryRequest) {
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

            // 模糊查询 - 商品spuId
            if (StringUtils.isNotEmpty(queryRequest.getSpuId())) {
                predicates.add(cbuild.equal(root.get("spuId"), queryRequest.getSpuId()));
            }

            // 模糊查询 - 商品skuId
            if (StringUtils.isNotEmpty(queryRequest.getSkuId())) {
                predicates.add(cbuild.like(root.get("skuId"), queryRequest.getSkuId()));
            }

            // 活动价
            if (queryRequest.getPrice() != null) {
                predicates.add(cbuild.equal(root.get("price"), queryRequest.getPrice()));
            }

            // 自提服务佣金
            if (queryRequest.getPickupCommission() != null) {
                predicates.add(cbuild.equal(root.get("pickupCommission"), queryRequest.getPickupCommission()));
            }

            // 帮卖佣金
            if (queryRequest.getAssistCommission() != null) {
                predicates.add(cbuild.equal(root.get("assistCommission"), queryRequest.getAssistCommission()));
            }

            // 活动库存
            if (queryRequest.getActivityStock() != null) {
                predicates.add(cbuild.equal(root.get("activityStock"), queryRequest.getActivityStock()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
