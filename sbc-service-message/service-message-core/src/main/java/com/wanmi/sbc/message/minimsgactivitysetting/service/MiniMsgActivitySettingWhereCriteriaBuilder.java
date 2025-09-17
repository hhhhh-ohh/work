package com.wanmi.sbc.message.minimsgactivitysetting.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingQueryRequest;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;
import com.wanmi.sbc.message.minimsgactivitysetting.model.root.MiniMsgActivitySetting;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>小程序订阅消息配置表动态查询条件构建器</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
public class MiniMsgActivitySettingWhereCriteriaBuilder {
    public static Specification<MiniMsgActivitySetting> build(MiniMsgActivitySettingQueryRequest queryRequest) {
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

            // 模糊查询 - 活动名称
            if (StringUtils.isNotEmpty(queryRequest.getActivityName())) {
                predicates.add(cbuild.like(root.get("activityName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getActivityName()))
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

            // 模糊查询 - 活动内容
            if (StringUtils.isNotEmpty(queryRequest.getContext())) {
                predicates.add(cbuild.like(root.get("context"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getContext()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 温馨提示
            if (StringUtils.isNotEmpty(queryRequest.getTips())) {
                predicates.add(cbuild.like(root.get("tips"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTips()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 要跳转的页面
            if (StringUtils.isNotEmpty(queryRequest.getToPage())) {
                predicates.add(cbuild.like(root.get("toPage"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getToPage()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 推送类型 0 立即发送  1 定时发送
            if (queryRequest.getType() != null) {
                predicates.add(cbuild.equal(root.get("type"), queryRequest.getType()));
            }

            // 大于或等于 搜索条件:定时发送时间开始
            if (queryRequest.getSendTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("sendTime"),
                        queryRequest.getSendTimeBegin()));
            }
            // 小于或等于 搜索条件:定时发送时间截止
            if (queryRequest.getSendTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("sendTime"),
                        queryRequest.getSendTimeEnd()));
            }

            // 预计推送人数
            if (queryRequest.getPreCount() != null) {
                predicates.add(cbuild.equal(root.get("preCount"), queryRequest.getPreCount()));
            }

            // 实际推送人数
            if (queryRequest.getRealCount() != null) {
                predicates.add(cbuild.equal(root.get("realCount"), queryRequest.getRealCount()));
            }

            // 推送状态 0：未推送，1：推送中，2：已推送，3：推送失败，4：部分失败
            if (queryRequest.getSendStatus() != null && queryRequest.getSendStatus() != ProgramSendStatus.ALL) {
                predicates.add(cbuild.equal(root.get("sendStatus"), queryRequest.getSendStatus()));
            }

            // 删除标识,0: 未删除 1: 已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
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

            // 模糊查询 - 创建人
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(cbuild.like(root.get("createPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCreatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:修改时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:修改时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            // 模糊查询 - 修改人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否已经扫描到 false 否  true 是
            if (Objects.nonNull(queryRequest.getScanFlag())) {
                predicates.add(cbuild.equal(root.get("scanFlag"), queryRequest.getScanFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
