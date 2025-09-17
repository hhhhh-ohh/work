package com.wanmi.sbc.marketing.communityregionsetting.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionLeaderSettingQueryRequest;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionLeaderSetting;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区拼团商家设置表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-20 14:55:16
 */
public class CommunityRegionLeaderSettingWhereCriteriaBuilder {
    public static Specification<CommunityRegionLeaderSetting> build(CommunityRegionLeaderSettingQueryRequest queryRequest) {
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

            // 区域id
            if (queryRequest.getRegionId() != null) {
                predicates.add(cbuild.equal(root.get("regionId"), queryRequest.getRegionId()));
            }

            // 批量区域id
            if (CollectionUtils.isNotEmpty(queryRequest.getRegionIds())) {
                predicates.add(root.get("regionId").in(queryRequest.getRegionIds()));
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

            // 批量自提点id
            if (CollectionUtils.isNotEmpty(queryRequest.getPickupPointIds())) {
                predicates.add(root.get("pickupPointId").in(queryRequest.getPickupPointIds()));
            }

            // 模糊查询 - 自提点id
            if (StringUtils.isNotEmpty(queryRequest.getPickupPointId())) {
                predicates.add(cbuild.like(root.get("pickupPointId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPickupPointId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }



            if (queryRequest.getNotRegionId() != null) {
                predicates.add(cbuild.notEqual(root.get("regionId"), queryRequest.getNotRegionId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
