package com.wanmi.sbc.order.paytimeseries.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesQueryRequest;
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 支付流水记录动态查询条件构建器
 *
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
public class PayTimeSeriesWhereCriteriaBuilder {
    public static Specification<PayTimeSeries> build(PayTimeSeriesQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-支付流水号List
            if (CollectionUtils.isNotEmpty(queryRequest.getPayNoList())) {
                predicates.add(root.get("payNo").in(queryRequest.getPayNoList()));
            }

            // 支付流水号
            if (StringUtils.isNotEmpty(queryRequest.getPayNo())) {
                predicates.add(cbuild.equal(root.get("payNo"), queryRequest.getPayNo()));
            }

            // 查询 - 订单id
            if (StringUtils.isNotEmpty(queryRequest.getBusinessId())) {
                predicates.add(cbuild.equal(root.get("businessId"), queryRequest.getBusinessId()));
            }

            // 批量查询-支订单idList
            if (CollectionUtils.isNotEmpty(queryRequest.getBusinessIdList())) {
                predicates.add(root.get("businessId").in(queryRequest.getBusinessIdList()));
            }

            // 申请价格
            if (queryRequest.getApplyPrice() != null) {
                predicates.add(cbuild.equal(root.get("applyPrice"), queryRequest.getApplyPrice()));
            }

            // 实际支付价格
            if (queryRequest.getPracticalPrice() != null) {
                predicates.add(
                        cbuild.equal(root.get("practicalPrice"), queryRequest.getPracticalPrice()));
            }

//            // 查询 - 返回结果
//            if (StringUtils.isNotEmpty(queryRequest.getResult())) {
//                predicates.add(cbuild.equal(root.get("result"), queryRequest.getResult()));
//            }

            // 状态
            if (queryRequest.getStatus() != null) {
                predicates.add(cbuild.equal(root.get("status"), queryRequest.getStatus()));
            }

            // 查询 - chargeId
            if (StringUtils.isNotEmpty(queryRequest.getChargeId())) {
                predicates.add(cbuild.equal(root.get("chargeId"), queryRequest.getChargeId()));
            }

            // 渠道id
            if (queryRequest.getChannelItemId() != null) {
                predicates.add(
                        cbuild.equal(root.get("channelItemId"), queryRequest.getChannelItemId()));
            }

            // 查询 - 请求ip
            if (StringUtils.isNotEmpty(queryRequest.getClientIp())) {
                predicates.add(cbuild.equal(root.get("clientIp"), queryRequest.getClientIp()));
            }

            // 大于或等于 搜索条件:创建时间开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("createTime"), queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:创建时间截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("createTime"), queryRequest.getCreateTimeEnd()));
            }

            // 大于或等于 搜索条件:回调时间开始
            if (queryRequest.getCallbackTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("callbackTime"), queryRequest.getCallbackTimeBegin()));
            }
            // 小于或等于 搜索条件:回调时间截止
            if (queryRequest.getCallbackTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("callbackTime"), queryRequest.getCallbackTimeEnd()));
            }

            // 大于或等于 搜索条件:退款时间开始
            if (queryRequest.getRefundTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("refundTime"), queryRequest.getRefundTimeBegin()));
            }
            // 小于或等于 搜索条件:退款时间截止
            if (queryRequest.getRefundTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("refundTime"), queryRequest.getRefundTimeEnd()));
            }

            // 退款状态
            if (queryRequest.getRefundStatus() != null) {
                predicates.add(
                        cbuild.equal(root.get("refundStatus"), queryRequest.getRefundStatus()));
            }

            // 查询 - 支付渠道，跟订单支付时候的渠道相对应
            if (StringUtils.isNotEmpty(queryRequest.getPayChannelType())) {
                predicates.add(
                        cbuild.equal(root.get("payChannelType"), queryRequest.getPayChannelType()));
            }

            // 查询 - 退款流水号
            if (StringUtils.isNotEmpty(queryRequest.getRefundPayNo())) {
                predicates.add(
                        cbuild.equal(root.get("refundPayNo"), queryRequest.getRefundPayNo()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
