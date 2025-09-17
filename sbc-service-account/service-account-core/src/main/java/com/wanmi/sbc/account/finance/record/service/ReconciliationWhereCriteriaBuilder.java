package com.wanmi.sbc.account.finance.record.service;

import com.wanmi.sbc.account.api.request.finance.record.AccountDetailsPageRequest;
import com.wanmi.sbc.account.bean.enums.OrderDeductionType;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.finance.record.model.entity.Reconciliation;
import com.wanmi.sbc.common.enums.QueryPayType;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.StringUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.wanmi.sbc.common.util.DateUtil.FMT_TIME_1;

/**
 * <p>动态查询条件构建器</p>
 * @author yangzhen
 * @date 2020-11-23 17:15:46
 */
public class ReconciliationWhereCriteriaBuilder {
    public static Specification<Reconciliation> build(AccountDetailsPageRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }



            // 支付方式
            if (Objects.nonNull(queryRequest.getPayWay())) {
                predicates.add(cbuild.equal(root.get("payWay"), queryRequest.getPayWay()));
            }

            // 查询支付方式
            if (Objects.nonNull(queryRequest.getQueryPayType())) {

                PayWay payWay;
                switch (queryRequest.getQueryPayType()) {
                    case ALIPAY:
                        //支付宝支付
                        payWay = PayWay.ALIPAY;
                        break;
                    case WECHAT:
                        //微信支付
                        payWay = PayWay.WECHAT;
                        break;
                    case UNIONPAY_B2B:
                        //企业银联支付
                        payWay = PayWay.UNIONPAY_B2B;
                        break;
                    case UNIONPAY:
                        //云闪付支付
                        payWay = PayWay.UNIONPAY;
                        break;
                    case BALANCE:
                        //余额支付
                        payWay = PayWay.BALANCE;
                        break;
                    case CREDIT:
                        //授信支付
                        payWay = PayWay.CREDIT;
                        break;
                    case OFFLINE:
                        //线下支付
                        payWay = PayWay.CASH;
                        break;
                    case POINT:
                        //纯积分支付
                        payWay = PayWay.POINT;
                        break;
                    case POINT_ALIPAY:
                        //积分+支付宝支付
                        payWay = PayWay.ALIPAY;
                        break;
                    case POINT_WECHAT:
                        //积分+微信支付
                        payWay = PayWay.WECHAT;
                        break;
                    case POINT_UNIONPAY_B2B:
                        //积分+企业银联支付
                        payWay = PayWay.UNIONPAY_B2B;
                        break;
                    case POINT_UNIONPAY:
                        //积分+云闪付
                        payWay = PayWay.UNIONPAY;
                        break;
                    case POINT_BALANCE:
                        //积分+余额支付
                        payWay = PayWay.BALANCE;
                        break;
                    case POINT_CREDIT:
                        //积分+授信支付
                        payWay = PayWay.CREDIT;
                        break;
                    case POINT_OFFLINE:
                        //积分+线下支付
                        payWay = PayWay.CASH;
                        break;
                    default:
                        payWay = null;
                        break;
                }

                if(Objects.nonNull(payWay) && !payWay.equals(PayWay.POINT)){
                    predicates.add(cbuild.equal(root.get("payWay"), payWay));
                    predicates.add(cbuild.greaterThan(root.get("amount"), NumberUtils.INTEGER_ZERO));
                }

//                if(queryRequest.getQueryPayType().name().startsWith(QueryPayType.POINT.name())){
//                    predicates.add(cbuild.greaterThan(root.get("points"), NumberUtils.INTEGER_ZERO));
//                }
            }

            //支付方式：在线支付
            if(Objects.nonNull(queryRequest.getPayType()) && queryRequest.getPayType() == PayType.ONLINE){
                CriteriaBuilder.In in = cbuild.in(root.get("payWay"));
                for (PayWay value : PayWay.values()) {
                    //支付方式不为线下支付或者优惠券的
                    if(!(value == PayWay.CASH || value == PayWay.COUPON)){
                        in.value(value);
                    }
                }
                predicates.add(in);
//                predicates.add(cbuild.greaterThan(root.get("amount"), NumberUtils.INTEGER_ZERO));
            }
            //支付方式：线下支付
            if(Objects.nonNull(queryRequest.getPayType()) && queryRequest.getPayType() == PayType.OFFLINE){
                predicates.add(cbuild.equal(root.get("payWay"), PayWay.CASH));
            }

            //抵扣方式：积分
            if(Objects.nonNull(queryRequest.getOrderDeductionType()) && queryRequest.getOrderDeductionType() == OrderDeductionType.POINT){
                predicates.add(cbuild.greaterThan(root.get("points"), NumberUtils.INTEGER_ZERO));
            }
            //抵扣方式礼品卡
            if(Objects.nonNull(queryRequest.getOrderDeductionType()) && queryRequest.getOrderDeductionType() == OrderDeductionType.GIFT_CARD){
                predicates.add(cbuild.greaterThan(root.get("giftCardPrice"), NumberUtils.INTEGER_ZERO));
            }

            if(StringUtils.isNotEmpty(queryRequest.getBeginTime())){
                LocalDateTime beginTime = DateUtil.parse(queryRequest.getBeginTime(), FMT_TIME_1);
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("tradeTime"),
                        beginTime));
            }


            // 小于或等于 搜索条件:订单/退单申请时间截止
            if(StringUtils.isNotEmpty(queryRequest.getEndTime())){
                LocalDateTime endTime = DateUtil.parse(queryRequest.getEndTime(), FMT_TIME_1);
                predicates.add(cbuild.lessThan(root.get("tradeTime"),
                        endTime));
            }

            // 交易类型，0：收入 1：退款
            if (queryRequest.getType() != null) {
                predicates.add(cbuild.equal(root.get("type"), queryRequest.getType()));
            }



            // 模糊查询 - 交易流水号
            if (StringUtils.isNotEmpty(queryRequest.getTradeNo())) {
                predicates.add(cbuild.like(root.get("tradeNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTradeNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
