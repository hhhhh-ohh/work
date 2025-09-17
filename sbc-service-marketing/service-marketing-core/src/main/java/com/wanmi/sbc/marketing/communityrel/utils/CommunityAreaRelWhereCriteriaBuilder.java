package com.wanmi.sbc.marketing.communityrel.utils;

import com.wanmi.sbc.marketing.api.request.communityrel.CommunityAreaRelQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import com.wanmi.sbc.marketing.communityrel.model.root.CommunityAreaRel;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区团购活动区域关联表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-24 14:38:10
 */
public class CommunityAreaRelWhereCriteriaBuilder {
    public static Specification<CommunityAreaRel> build(CommunityAreaRelQueryRequest queryRequest) {
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

            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getActivityIdList())) {
                predicates.add(root.get("activityId").in(queryRequest.getActivityIdList()));
            }

            // 活动id
            if (StringUtils.isNotEmpty(queryRequest.getActivityId())) {
                predicates.add(cbuild.equal(root.get("activityId"), queryRequest.getActivityId()));
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

            // 批量查询-区域IdList
            if (CollectionUtils.isNotEmpty(queryRequest.getAreaIdList())) {
                predicates.add(root.get("areaId").in(queryRequest.getAreaIdList()));
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

            // 销售渠道 0:自主销售 1:团长帮卖
            if (queryRequest.getSalesType() != null) {
                predicates.add(cbuild.equal(root.get("salesType"), queryRequest.getSalesType()));
            }

            // 模糊查询 - 区域Id
            if (StringUtils.isNotEmpty(queryRequest.getAreaId())) {
                predicates.add(cbuild.like(root.get("areaId"), queryRequest.getAreaId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
