package com.wanmi.sbc.order.trade.request;

import com.wanmi.sbc.order.api.request.trade.CreditTradePageRequest;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Objects;

/**
 * @author houshuai
 * @date 2021/3/2 14:11
 * @description <p> 未还款订单查询 </p>
 */
public class CreditTradeWhereCriteriaBuilder {
    /**
     * mongo shell 查询
     *
     * @return
     */
    public static Criteria getRepayCriteria(CreditTradePageRequest request) {
        Criteria criteria = new Criteria();
        //当前授信周期内
        if (Objects.nonNull(request.getStartTime()) && Objects.nonNull(request.getEndTime())) {
            //查询mongo 时区问题
            criteria.and("tradeState.payTime").gte(request.getStartTime()).lte(request.getEndTime());
        }
        //已完成订单
        if (StringUtils.isNotBlank(request.getFlowState())) {
            criteria.and("tradeState.flowState").is(request.getFlowState());
        }
        //已支付订单--已支付定金
        if (CollectionUtils.isNotEmpty(request.getPayOrderStatusList())) {
            criteria.and("tradeState.payState").in(request.getPayOrderStatusList());
        }
        //是否已还款
        if (Objects.nonNull(request.getHasRepaid())) {
            criteria.and("creditPayInfo.hasRepaid").is(request.getHasRepaid());
        }
        //是否需要还款
        if (Objects.nonNull(request.getNeedCreditRepayFlag())) {
            criteria.and("needCreditRepayFlag").is(request.getNeedCreditRepayFlag());
        }
        //是否为授信支付
        criteria.and("payWay").ne(null);
        //creditPayInfo不等于null的为授信支付
        criteria.and("creditPayInfo").ne(null);
        criteria.and("creditPayInfo.startTime").is(request.getStartTime());
        criteria.and("creditPayInfo.endTime").is(request.getEndTime());
        criteria.and("buyer._id").is(request.getCustomerId());

        return criteria;
    }
}
