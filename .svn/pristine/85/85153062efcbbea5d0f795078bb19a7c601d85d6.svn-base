package com.wanmi.sbc.marketing.communityactivity.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CommunityLeaderRangeType;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
public class CommunityActivityWhereCriteriaBuilder {
    public static Specification<CommunityActivity> build(CommunityActivityQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getActivityIdList())) {
                predicates.add(root.get("activityId").in(queryRequest.getActivityIdList()));
            }

            // 主键
            if (StringUtils.isNotEmpty(queryRequest.getActivityId())) {
                predicates.add(cbuild.equal(root.get("activityId"), queryRequest.getActivityId()));
            }

            // 店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 批量-店铺id
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreIds())) {
                predicates.add(root.get("storeId").in(queryRequest.getStoreIds()));
            }

            // 模糊查询 - 活动名称
            if (StringUtils.isNotEmpty(queryRequest.getActivityName())) {
                predicates.add(cbuild.like(root.get("activityName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getActivityName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 活动描述
            if (StringUtils.isNotEmpty(queryRequest.getDescription())) {
                predicates.add(cbuild.like(root.get("description"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDescription()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:开始时间开始
            if (queryRequest.getStartTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("startTime"),
                        queryRequest.getStartTimeBegin()));
            }
            // 小于或等于 搜索条件:开始时间截止
            if (queryRequest.getStartTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("startTime"),
                        queryRequest.getStartTimeEnd()));
            }

            // 大于或等于 搜索条件:结束时间开始
            if (queryRequest.getEndTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTimeBegin()));
            }
            // 小于或等于 搜索条件:结束时间截止
            if (queryRequest.getEndTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTimeEnd()));
            }

            // 小于或等于 搜索条件:延时结束时间截止
            if (queryRequest.getRealEndTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("realEndTime"),
                        queryRequest.getRealEndTimeEnd()));
            }

            // 活动时间状态
            if(CommunityTabStatus.STARTED.equals(queryRequest.getTabType())){
                LocalDateTime now = LocalDateTime.now();
                predicates.add(cbuild.lessThanOrEqualTo(root.get("startTime"), now));
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), now));
            }else if(CommunityTabStatus.ENDED.equals(queryRequest.getTabType())){
                predicates.add(cbuild.lessThan(root.get("endTime"), LocalDateTime.now()));
            }else if(CommunityTabStatus.NOT_START.equals(queryRequest.getTabType())){
                predicates.add(cbuild.greaterThan(root.get("startTime"), LocalDateTime.now()));
            }

            // 开始时间
            if(queryRequest.getStartTime() != null){
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("startTime"), queryRequest.getStartTime()));
            }

            // 结束时间
            if(queryRequest.getEndTime() != null){
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"), queryRequest.getEndTime()));
            }

            // 模糊查询 - 物流方式 0:自提 1:快递 以逗号拼凑
            if (CollectionUtils.isNotEmpty(queryRequest.getLogisticsTypes())) {
                List<Predicate> orP = queryRequest.getLogisticsTypes().stream()
                        .map(t -> cbuild.like(root.get("logisticsType"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(String.valueOf(t.toValue())))
                        .concat(StringUtil.SQL_LIKE_CHAR))).collect(Collectors.toList());
                predicates.add(cbuild.or(orP.toArray(new Predicate[queryRequest.getLogisticsTypes().size()])));
            }

            //搜索条件-限帮卖团长相关
            if(Boolean.TRUE.equals(queryRequest.getAssistLeaderFlag())) {
                //全部 or 帮卖活动id
                predicates.add(cbuild.or(
                        cbuild.equal(root.get("leaderRange"), CommunityLeaderRangeType.ALL),
                        root.get("activityId").in(queryRequest.getAssistActivityIds())
                ));
            }

            // 模糊查询 - 销售渠道 0:自主销售 1:团长帮卖 以逗号拼凑
            if (CollectionUtils.isNotEmpty(queryRequest.getSalesTypes())) {
                List<Predicate> orP = queryRequest.getSalesTypes().stream().map(t ->
                        cbuild.like(root.get("salesType"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(String.valueOf(t.toValue())))
                        .concat(StringUtil.SQL_LIKE_CHAR))).collect(Collectors.toList());
                predicates.add(cbuild.or(orP.toArray(new Predicate[queryRequest.getSalesTypes().size()])));
            }

            // 自主销售范围 0：全部 1：地区 2：自定义
            if (queryRequest.getSalesRange() != null) {
                predicates.add(cbuild.equal(root.get("salesRange"), queryRequest.getSalesRange()));
            }

            // 帮卖团长范围 0：全部 1：地区 2：自定义
            if (queryRequest.getLeaderRange() != null) {
                predicates.add(cbuild.equal(root.get("leaderRange"), queryRequest.getLeaderRange()));
            }

            // 佣金设置 0：商品 1：按团长/自提点
            if (queryRequest.getCommissionFlag() != null) {
                predicates.add(cbuild.equal(root.get("commissionFlag"), queryRequest.getCommissionFlag()));
            }

            // 批量-自提服务佣金
            if (queryRequest.getPickupCommission() != null) {
                predicates.add(cbuild.equal(root.get("pickupCommission"), queryRequest.getPickupCommission()));
            }

            // 批量-帮卖团长佣金
            if (queryRequest.getAssistCommission() != null) {
                predicates.add(cbuild.equal(root.get("assistCommission"), queryRequest.getAssistCommission()));
            }

            // 模糊查询 - 团详情
            if (StringUtils.isNotEmpty(queryRequest.getDetails())) {
                predicates.add(cbuild.like(root.get("details"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDetails()))
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

            // 大于或等于 搜索条件:结束时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:结束时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            // 是否生成 0:未生成 1:已生成
            if (queryRequest.getGenerateFlag() != null) {
                predicates.add(cbuild.equal(root.get("generateFlag"), queryRequest.getGenerateFlag()));
            }

            // 非当前id
            if (StringUtils.isNotBlank(queryRequest.getNotId())) {
                predicates.add(cbuild.notEqual(root.get("activityId"), queryRequest.getNotId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
