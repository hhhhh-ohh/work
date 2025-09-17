package com.wanmi.sbc.customer.communityleader.service;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderQueryRequest;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.communityleader.model.root.CommunityLeader;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区团购团长表动态查询条件构建器</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
public class CommunityLeaderWhereCriteriaBuilder {
    public static Specification<CommunityLeader> build(CommunityLeaderQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-团长idList
            if (CollectionUtils.isNotEmpty(queryRequest.getLeaderIdList())) {
                predicates.add(root.get("leaderId").in(queryRequest.getLeaderIdList()));
            }

            // 批量查询-区域下团长idList
            if (CollectionUtils.isNotEmpty(queryRequest.getAreaLeaderIdList())) {
                predicates.add(root.get("leaderId").in(queryRequest.getAreaLeaderIdList()));
            }

            // 团长id
            if (StringUtils.isNotEmpty(queryRequest.getLeaderId())) {
                predicates.add(cbuild.equal(root.get("leaderId"), queryRequest.getLeaderId()));
            }

            // 团长id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), queryRequest.getCustomerId()));
            }

            // 团长账号
            if (StringUtils.isNotEmpty(queryRequest.getLeaderAccount())) {
                predicates.add(cbuild.equal(root.get("leaderAccount"), queryRequest.getLeaderAccount()));
            }

            // 审核状态, 0:未审核 1:审核通过 2:审核失败 3:禁用中
            if (queryRequest.getCheckStatus() != null) {
                predicates.add(cbuild.equal(root.get("checkStatus"), queryRequest.getCheckStatus()));
            }

            // 是否只查询已审核或者禁用中的数据
            if (queryRequest.getIsCheck() != null && queryRequest.getIsCheck().equals(Constants.yes)) {
                Predicate p1 = cbuild.equal(root.get("checkStatus"), LeaderCheckStatus.CHECKED);
                Predicate p2 = cbuild.equal(root.get("checkStatus"), LeaderCheckStatus.FORBADE);
                predicates.add(cbuild.or(p1,p2));
            }

            // 大于或等于 搜索条件:审核时间开始
            if (queryRequest.getCheckTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("checkTime"),
                        queryRequest.getCheckTimeBegin()));
            }
            // 小于或等于 搜索条件:审核时间截止
            if (queryRequest.getCheckTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("checkTime"),
                        queryRequest.getCheckTimeEnd()));
            }

            // 大于或等于 搜索条件:禁用时间开始
            if (queryRequest.getDisableTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("disableTime"),
                        queryRequest.getDisableTimeBegin()));
            }
            // 小于或等于 搜索条件:禁用时间截止
            if (queryRequest.getDisableTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("disableTime"),
                        queryRequest.getDisableTimeEnd()));
            }

            // 0:否 1:是
            if (queryRequest.getAssistFlag() != null) {
                predicates.add(cbuild.equal(root.get("assistFlag"), queryRequest.getAssistFlag()));
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

            // 删除标识
            if(queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
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

            // 模糊查询 - 团长简介
            if (StringUtils.isNotEmpty(queryRequest.getLeaderDescription())) {
                predicates.add(cbuild.like(root.get("leaderDescription"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLeaderDescription()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 驳回原因
            if (StringUtils.isNotEmpty(queryRequest.getCheckReason())) {
                predicates.add(cbuild.like(root.get("checkReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCheckReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 禁用原因
            if (StringUtils.isNotEmpty(queryRequest.getDisableReason())) {
                predicates.add(cbuild.like(root.get("disableReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDisableReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 关键词搜索 - 模糊名称、模糊账号
            if(StringUtils.isNotBlank(queryRequest.getKeyword())){
                predicates.add(cbuild.or(
                        cbuild.like(root.get("leaderName"), StringUtil.SQL_LIKE_CHAR
                                .concat(XssUtils.replaceLikeWildcard(queryRequest.getKeyword()))
                                .concat(StringUtil.SQL_LIKE_CHAR)),
                        cbuild.like(root.get("leaderAccount"), StringUtil.SQL_LIKE_CHAR
                                .concat(XssUtils.replaceLikeWildcard(queryRequest.getKeyword()))
                                .concat(StringUtil.SQL_LIKE_CHAR))
                ));
            }

            // 团长id
            if (StringUtils.isNotEmpty(queryRequest.getNotLeaderId())) {
                predicates.add(cbuild.notEqual(root.get("leaderId"), queryRequest.getNotLeaderId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
