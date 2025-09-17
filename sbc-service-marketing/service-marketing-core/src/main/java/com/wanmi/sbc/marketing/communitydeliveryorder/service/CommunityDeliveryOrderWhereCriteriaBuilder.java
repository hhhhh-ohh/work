package com.wanmi.sbc.marketing.communitydeliveryorder.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderQueryRequest;
import com.wanmi.sbc.marketing.communitydeliveryorder.model.root.CommunityDeliveryOrder;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区团购发货单动态查询条件构建器</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
public class CommunityDeliveryOrderWhereCriteriaBuilder {
    public static Specification<CommunityDeliveryOrder> build(CommunityDeliveryOrderQueryRequest queryRequest) {
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

            // 模糊查询 - 团长Id
            if (StringUtils.isNotEmpty(queryRequest.getLeaderId())) {
                predicates.add(cbuild.like(root.get("leaderId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLeaderId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 区域名称
            if (StringUtils.isNotEmpty(queryRequest.getAreaName())) {
                predicates.add(cbuild.like(root.get("areaName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAreaName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商品json内容
            if (StringUtils.isNotEmpty(queryRequest.getGoodsContext())) {
                predicates.add(cbuild.like(root.get("goodsContext"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsContext()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 发货状态 0:未发货 1:已发货
            if (queryRequest.getDeliveryStatus() != null) {
                predicates.add(cbuild.equal(root.get("deliveryStatus"), queryRequest.getDeliveryStatus()));
            }

            // 汇总类型 0:按团长 1:按区域
            if (queryRequest.getType() != null) {
                predicates.add(cbuild.equal(root.get("type"), queryRequest.getType()));
            }

            // 模糊查询 - 团长名称
            if (StringUtils.isNotEmpty(queryRequest.getLeaderName())) {
                predicates.add(cbuild.like(root.get("leaderName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLeaderName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 自提点名称
            if (StringUtils.isNotEmpty(queryRequest.getPickupPointName())) {
                predicates.add(cbuild.like(root.get("pickupPointName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPickupPointName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 联系电话
            if (StringUtils.isNotEmpty(queryRequest.getContactNumber())) {
                predicates.add(cbuild.like(root.get("contactNumber"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getContactNumber()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 全详细地址
            if (StringUtils.isNotEmpty(queryRequest.getFullAddress())) {
                predicates.add(cbuild.like(root.get("fullAddress"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getFullAddress()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
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

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
