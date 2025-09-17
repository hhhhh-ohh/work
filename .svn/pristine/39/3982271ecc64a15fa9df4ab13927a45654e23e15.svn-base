package com.wanmi.sbc.order.payorder.request;

import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by zhangjin on 2017/8/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayOrderGenerateRequest extends BaseRequest {

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 会员主键
     */
    private String customerId;

    /**
     * 收款金额
     */
    private BigDecimal payOrderPrice;

    /**
     * 积分数
     */
    private Long payOrderPoints;

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 商家id
     */
    private Long companyInfoId;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 订单类型
     */
    private OrderType orderType;

    /**
     * 订单礼品卡抵扣金额
     */
    private BigDecimal giftCardPrice;

    /**
     * 礼品卡类型
     */
    private GiftCardType giftCardType;
}
