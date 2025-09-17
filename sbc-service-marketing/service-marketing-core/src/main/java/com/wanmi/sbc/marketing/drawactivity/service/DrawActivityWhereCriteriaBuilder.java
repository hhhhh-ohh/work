package com.wanmi.sbc.marketing.drawactivity.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.drawactivity.DrawActivityQueryRequest;
import com.wanmi.sbc.marketing.drawactivity.model.root.DrawActivity;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>抽奖活动表动态查询条件构建器</p>
 *
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
public class DrawActivityWhereCriteriaBuilder {
    public static Specification<DrawActivity> build(DrawActivityQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 是否删除标志 0：否，1：是
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }else {
                predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));
            }

            // 模糊查询 - 活动名称
            if (StringUtils.isNotEmpty(queryRequest.getActivityName())) {
                predicates.add(cbuild.like(root.get("activityName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getActivityName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:开始时间开始
            if (queryRequest.getStartTime() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("startTime"),
                        queryRequest.getStartTime()));
            }

            // 小于或等于 搜索条件:结束时间截止
            if (queryRequest.getEndTime() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTime()));
            }
            LocalDateTime now = LocalDateTime.now();
            switch (queryRequest.getQueryTab()) {
                case STARTED://进行中
                    predicates.add(cbuild.equal(root.get("pauseFlag"), 0));
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("startTime"), now));
                    predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), now));
                    break;
                case PAUSED://暂停中
                    predicates.add(cbuild.equal(root.get("pauseFlag"), 1));
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("startTime"), now));
                    predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), now));
                    break;
                case NOT_START://未开始
                    predicates.add(cbuild.greaterThan(root.get("startTime"), now));
                    break;
                case ENDED://已结束
                    predicates.add(cbuild.lessThan(root.get("endTime"), now));
                    break;
                case NOT_ENDED://不包括已结束的所有
                    predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), now));
                    break;
                default:
                    break;
            }

            // 主键
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 抽奖次数限制类型（0：每日，1：每人）
            if (queryRequest.getDrawTimesType() != null) {
                predicates.add(cbuild.equal(root.get("drawTimesType"), queryRequest.getDrawTimesType()));
            }

            // 抽奖次数，默认为0
            if (queryRequest.getDrawTimes() != null) {
                predicates.add(cbuild.equal(root.get("drawTimes"), queryRequest.getDrawTimes()));
            }

            // 中奖次数限制类型 （0：无限制，1：每人每天）
            if (queryRequest.getWinTimesType() != null) {
                predicates.add(cbuild.equal(root.get("winTimesType"), queryRequest.getWinTimesType()));
            }

            // 每人每天最多中奖次数，默认为0
            if (queryRequest.getWinTimes() != null) {
                predicates.add(cbuild.equal(root.get("winTimes"), queryRequest.getWinTimes()));
            }

            // 模糊查询 - 未中奖提示
            if (StringUtils.isNotEmpty(queryRequest.getNotAwardTip())) {
                predicates.add(cbuild.like(root.get("notAwardTip"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getNotAwardTip()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 抽奖次数上限提示
            if (StringUtils.isNotEmpty(queryRequest.getMaxAwardTip())) {
                predicates.add(cbuild.like(root.get("maxAwardTip"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMaxAwardTip()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 活动规则说明
            if (StringUtils.isNotEmpty(queryRequest.getActivityContent())) {
                predicates.add(cbuild.like(root.get("activityContent"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getActivityContent()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 实际抽奖人/次
            if (queryRequest.getDrawCount() != null) {
                predicates.add(cbuild.equal(root.get("drawCount"), queryRequest.getDrawCount()));
            }

            // 实际中奖人/次
            if (queryRequest.getAwardCount() != null) {
                predicates.add(cbuild.equal(root.get("awardCount"), queryRequest.getAwardCount()));
            }
            // 目标客户
            if (StringUtils.isNotBlank(queryRequest.getJoinLevel())) {
                //boss端搜索项 -1 全部客户 0 部分客户
                if (queryRequest.getJoinLevel().equals(Constants.STR_MINUS_1)) {
                    predicates.add(cbuild.equal(root.get("joinLevel"), queryRequest.getJoinLevel()));
                } else {
                    Expression<Integer> expression = cbuild.function("FIND_IN_SET", Integer.class, cbuild.literal(queryRequest.getJoinLevel()), root.get("joinLevel"));
                    predicates.add(cbuild.greaterThan(expression, 0));
                }
            }
            // 不查暂停状态的 true 不查
            if (Objects.nonNull(queryRequest.getNotPausedFlag()) && queryRequest.getNotPausedFlag()) {
                predicates.add(cbuild.notEqual(root.get("pauseFlag"), 1));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
