package com.wanmi.sbc.marketing.communityregionsetting.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingQueryRequest;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionSetting;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区拼团商家设置表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
public class CommunityRegionSettingWhereCriteriaBuilder {
    public static Specification<CommunityRegionSetting> build(CommunityRegionSettingQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-区域idList
            if (CollectionUtils.isNotEmpty(queryRequest.getRegionIdList())) {
                predicates.add(root.get("regionId").in(queryRequest.getRegionIdList()));
            }

            // 区域id
            if (queryRequest.getRegionId() != null) {
                predicates.add(cbuild.equal(root.get("regionId"), queryRequest.getRegionId()));
            }

            // 店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 批量查询-区域idList
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreIdList())) {
                predicates.add(root.get("storeId").in(queryRequest.getStoreIdList()));
            }

            // 区域名称
            if (StringUtils.isNotBlank(queryRequest.getRegionName())) {
                predicates.add(cbuild.equal(root.get("regionName"), queryRequest.getRegionName().trim()));
            }

            // 模糊查询 - 区域名称
            if (StringUtils.isNotEmpty(queryRequest.getLikeRegionName())) {
                predicates.add(cbuild.like(root.get("regionName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLikeRegionName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 区域id
            if (queryRequest.getNotRegionId() != null) {
                predicates.add(cbuild.notEqual(root.get("regionId"), queryRequest.getNotRegionId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
