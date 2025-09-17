package com.wanmi.sbc.order.payingmemberpayrecord.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordQueryRequest;
import com.wanmi.sbc.order.payingmemberpayrecord.model.root.PayingMemberPayRecord;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>付费会员支付记录表动态查询条件构建器</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
public class PayingMemberPayRecordWhereCriteriaBuilder {
    public static Specification<PayingMemberPayRecord> build(PayingMemberPayRecordQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 业务id
            if (StringUtils.isNotEmpty(queryRequest.getBusinessId())) {
                predicates.add(cbuild.like(root.get("businessId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getBusinessId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - chargeId
            if (StringUtils.isNotEmpty(queryRequest.getChargeId())) {
                predicates.add(cbuild.like(root.get("chargeId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getChargeId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 申请价格
            if (queryRequest.getApplyPrice() != null) {
                predicates.add(cbuild.equal(root.get("applyPrice"), queryRequest.getApplyPrice()));
            }

            // 实际成功交易价格
            if (queryRequest.getPracticalPrice() != null) {
                predicates.add(cbuild.equal(root.get("practicalPrice"), queryRequest.getPracticalPrice()));
            }

            // 状态:0处理中(退款状态)/未支付(支付状态) 1成功 2失败
            if (queryRequest.getStatus() != null) {
                predicates.add(cbuild.equal(root.get("status"), queryRequest.getStatus()));
            }

            // 支付渠道项id
            if (queryRequest.getChannelItemId() != null) {
                predicates.add(cbuild.equal(root.get("channelItemId"), queryRequest.getChannelItemId()));
            }

            // 大于或等于 搜索条件:回调时间开始
            if (queryRequest.getCallbackTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("callbackTime"),
                        queryRequest.getCallbackTimeBegin()));
            }
            // 小于或等于 搜索条件:回调时间截止
            if (queryRequest.getCallbackTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("callbackTime"),
                        queryRequest.getCallbackTimeEnd()));
            }

            // 大于或等于 搜索条件:交易完成时间开始
            if (queryRequest.getFinishTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("finishTime"),
                        queryRequest.getFinishTimeBegin()));
            }
            // 小于或等于 搜索条件:交易完成时间截止
            if (queryRequest.getFinishTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("finishTime"),
                        queryRequest.getFinishTimeEnd()));
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

            // 模糊查询 - createPerson
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(cbuild.like(root.get("createPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCreatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
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

            // 模糊查询 - updatePerson
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识：0：未删除；1：已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
