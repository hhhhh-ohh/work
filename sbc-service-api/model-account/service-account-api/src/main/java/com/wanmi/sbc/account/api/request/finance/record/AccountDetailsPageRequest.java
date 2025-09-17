package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.account.bean.enums.AccountRecordType;
import com.wanmi.sbc.account.bean.enums.OrderDeductionType;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.enums.QueryPayType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>对账明细查询参数分页结构</p>
 * Created by of628-wenzhi on 2017-12-08-下午5:02.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountDetailsPageRequest extends BasePageRequest {

    private static final long serialVersionUID = 1315276847532565635L;
    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private PayWay payWay;

    /**
     * 查询支付方式
     */
    @Schema(description = "查询支付方式")
    private QueryPayType queryPayType;

    /**
     * 0 在线支付 1线下支付
     */
    @Schema(description = "支付方式",contentSchema = com.wanmi.sbc.account.bean.enums.PayType.class)
    private PayType payType;

    /**
     * 抵扣方式
     */
    @Schema(description = "支付渠道，0：积分 1：礼品卡")
    private OrderDeductionType orderDeductionType;

    /**
     * 开始时间,非空，格式：yyyy-MM-dd HH:mm:ss
     */
    @Schema(description = "开始时间")
    @NotNull
    private String beginTime;

    /**
     * 结束时间，非空，格式：yyyy-MM-dd HH:mm:ss
     */
    @Schema(description = "结束时间")
    @NotNull
    private String endTime;

    /**
     * 对账类型 {@link AccountRecordType}
     */
    @Schema(description = "对账类型")
    private AccountRecordType accountRecordType;

    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String tradeNo;

    /**
     * 交易类型，0：收入 1：退款
     */
    @Schema(description = "交易类型，0：收入 1：退款")
    private Integer type;

}
