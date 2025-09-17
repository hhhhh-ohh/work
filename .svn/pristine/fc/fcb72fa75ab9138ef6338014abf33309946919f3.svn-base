package com.wanmi.sbc.customer.communitypickup.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointQueryRequest;
import com.wanmi.sbc.customer.communitypickup.model.root.CommunityLeaderPickupPoint;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>团长自提点表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
public class CommunityLeaderPickupPointWhereCriteriaBuilder {
    public static Specification<CommunityLeaderPickupPoint> build(CommunityLeaderPickupPointQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-自提点idList
            if (CollectionUtils.isNotEmpty(queryRequest.getPickupPointIdList())) {
                predicates.add(root.get("pickupPointId").in(queryRequest.getPickupPointIdList()));
            }

            // 自提点id
            if (StringUtils.isNotEmpty(queryRequest.getPickupPointId())) {
                predicates.add(cbuild.equal(root.get("pickupPointId"), queryRequest.getPickupPointId()));
            }

            // 团长id
            if (StringUtils.isNotEmpty(queryRequest.getLeaderId())) {
                predicates.add(cbuild.equal(root.get("leaderId"), queryRequest.getLeaderId()));
            }

            // 团长id
            if (CollectionUtils.isNotEmpty(queryRequest.getLeaderIds())) {
                predicates.add(root.get("leaderId").in(queryRequest.getLeaderIds()));
            }

            // 会员id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), queryRequest.getCustomerId()));
            }

            // 批量查询-自提点idList
            if (CollectionUtils.isNotEmpty(queryRequest.getLeaderIds())) {
                predicates.add(root.get("leaderId").in(queryRequest.getLeaderIds()));
            }

            // 批量查询-帮卖人自提点idList
            if (CollectionUtils.isNotEmpty(queryRequest.getAssistLeaderIds())) {
                predicates.add(root.get("leaderId").in(queryRequest.getAssistLeaderIds()));
            }

            // 团长账号
            if (StringUtils.isNotEmpty(queryRequest.getLeaderAccount())) {
                predicates.add(cbuild.equal(root.get("leaderAccount"), queryRequest.getLeaderAccount()));
            }

            // 审核状态, 0:未审核 1审核通过 2审核失败 3禁用中
            if (queryRequest.getCheckStatus() != null) {
                predicates.add(cbuild.equal(root.get("checkStatus"), queryRequest.getCheckStatus()));
            }

            // 自提点省份
            if (queryRequest.getPickupProvinceId() != null) {
                predicates.add(cbuild.equal(root.get("pickupProvinceId"), queryRequest.getPickupProvinceId()));
            }

            // 自提点城市
            if (queryRequest.getPickupCityId() != null) {
                predicates.add(cbuild.equal(root.get("pickupCityId"), queryRequest.getPickupCityId()));
            }

            // 自提点区县
            if (queryRequest.getPickupAreaId() != null) {
                predicates.add(cbuild.equal(root.get("pickupAreaId"), queryRequest.getPickupAreaId()));
            }

            // 自提点街道
            if (queryRequest.getPickupStreetId() != null) {
                predicates.add(cbuild.equal(root.get("pickupStreetId"), queryRequest.getPickupStreetId()));
            }

            // 经度
            if (queryRequest.getLng() != null) {
                predicates.add(cbuild.equal(root.get("lng"), queryRequest.getLng()));
            }

            // 纬度
            if (queryRequest.getLat() != null) {
                predicates.add(cbuild.equal(root.get("lat"), queryRequest.getLat()));
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

            //自提点id
            if(CollectionUtils.isNotEmpty(queryRequest.getAreaIds())){
                predicates.add(cbuild.or(
                    root.get("pickupProvinceId").in(queryRequest.getAreaIds()),
                    root.get("pickupCityId").in(queryRequest.getAreaIds()),
                    root.get("pickupAreaId").in(queryRequest.getAreaIds())
                ));
            }

            // 模糊查询 - 团长账号
            if (StringUtils.isNotEmpty(queryRequest.getLikeLeaderAccount())) {
                predicates.add(cbuild.like(root.get("leaderAccount"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLikeLeaderAccount()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 团长名称
            if (StringUtils.isNotEmpty(queryRequest.getLikeLeaderName())) {
                predicates.add(cbuild.like(root.get("leaderName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLikeLeaderName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 自提点名称
            if (StringUtils.isNotEmpty(queryRequest.getPickupPointName())) {
                predicates.add(cbuild.like(root.get("pickupPointName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPickupPointName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 详细地址
            if (StringUtils.isNotEmpty(queryRequest.getAddress())) {
                predicates.add(cbuild.like(root.get("address"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAddress()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 联系电话
            if (StringUtils.isNotEmpty(queryRequest.getContactNumber())) {
                predicates.add(cbuild.like(root.get("contactNumber"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getContactNumber()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 自提时间
            if (StringUtils.isNotEmpty(queryRequest.getPickupTime())) {
                predicates.add(cbuild.like(root.get("pickupTime"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPickupTime()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 全详细地址
            if (StringUtils.isNotEmpty(queryRequest.getFullAddress())) {
                predicates.add(cbuild.like(root.get("fullAddress"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getFullAddress()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识 0:未删除 1:已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 批量查询-自提点idList
            if (CollectionUtils.isNotEmpty(queryRequest.getNotPickupPointIdList())) {
                predicates.add(cbuild.not(root.get("pickupPointId").in(queryRequest.getNotPickupPointIdList())));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
