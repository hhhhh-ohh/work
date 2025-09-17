package com.wanmi.sbc.order.api.request.refund;


import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.order.bean.dto.RefundOrderRequestDTO;
import com.wanmi.sbc.common.enums.QueryPayType;
import com.wanmi.sbc.order.bean.enums.OrderDeductionType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 退款单
 * Created by zhangjin on 2017/4/21.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class RefundOrderPageRequest extends RefundOrderRequestDTO {

    private static final long serialVersionUID = -5620693206009388482L;

    /**
     * 筛选支付方式
     */
    @Schema(description = "筛选支付方式")
    private QueryPayType queryPayType;

    /**
     * 0 在线支付 1线下支付
     */
    @Schema(description = "支付方式",contentSchema = com.wanmi.sbc.account.bean.enums.PayType.class)
    private Integer payType;

    /**
     * 支付渠道
     */
    @Schema(description = "支付渠道，0：微信 1：支付宝 5：企业银联 6：云闪付 8：余额 9：授信 10：拉卡拉")
    private PayWay payWay;

    /**
     * 抵扣方式
     */
    @Schema(description = "支付渠道，0：积分 1：礼品卡")
    private OrderDeductionType orderDeductionType;

    /**
     * 负责业务员集合
     */
    private List<String> employeeIds;
}
