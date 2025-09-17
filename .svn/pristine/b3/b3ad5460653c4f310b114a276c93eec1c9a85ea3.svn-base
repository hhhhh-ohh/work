package com.wanmi.sbc.marketing.communitysetting.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingQueryRequest;
import com.wanmi.sbc.marketing.communitysetting.model.root.CommunitySetting;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区拼团商家设置表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
public class CommunitySettingWhereCriteriaBuilder {
    public static Specification<CommunitySetting> build(CommunitySettingQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-店铺idList
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreIdList())) {
                predicates.add(root.get("storeId").in(queryRequest.getStoreIdList()));
            }

            // 店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 模糊查询 - 汇总类型 0:按团长 1:按区域 以逗号拼凑
            if (StringUtils.isNotEmpty(queryRequest.getDeliveryOrderType())) {
                predicates.add(cbuild.like(root.get("deliveryOrderType"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDeliveryOrderType()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 区域汇总类型 0：省份1：城市2：自定义
            if (queryRequest.getDeliveryAreaType() != null) {
                predicates.add(cbuild.equal(root.get("deliveryAreaType"), queryRequest.getDeliveryAreaType()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
