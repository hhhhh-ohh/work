package com.wanmi.sbc.account.api.request.finance.record;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>获取单条结算单信息request</p>
 * Created by daiyitian on 2018-10-13-下午6:29.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementGetViewRequest extends BaseRequest {

    private static final long serialVersionUID = -7300475600047961244L;
    /**
     * 用于生成结算单号，结算单号自增
     */
    @Schema(description = "用于生成结算单号，结算单号自增")
    @NotNull
    private Long settleId;

    /**
     * 结算单uuid，mongodb外键
     */
    @Schema(description = "结算单uuid，mongodb外键")
    private String settleUuid;

    /**
     * 结算单创建时间
     */
    @Schema(description = "结算单创建时间")
    private Date createTime;

    /**
     * 结算单号
     */
    @Schema(description = "结算单号")
    private String settleCode;

    /**
     * 结算单开始时间
     */
    @Schema(description = "结算单开始时间")
    private String startTime;

    /**
     * 结算单结束时间
     */
    @Schema(description = "结算单结束时间")
    private String endTime;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    @NotNull
    private Long storeId;

    /**
     * 交易总额
     */
    @Schema(description = "交易总额")
    private BigDecimal salePrice;

    /**
     * 商品销售总数
     */
    @Schema(description = "商品销售总数")
    private long saleNum;

    /**
     * 退款总额
     */
    @Schema(description = "退款总额")
    private BigDecimal returnPrice;

    /**
     * 商品退货总数
     */
    @Schema(description = "商品退货总数")
    private long returnNum;

    /**
     * 平台佣金总额
     */
    @Schema(description = "平台佣金总额")
    private BigDecimal platformPrice;

    /**
     * 店铺应收
     */
    @Schema(description = "店铺应收")
    private BigDecimal storePrice;

    /**
     * 总运费
     */
    @Schema(description = "总运费")
    private BigDecimal deliveryPrice;

    /**
     * 结算状态 {@link SettleStatus}
     */
    @Schema(description = "结算状态")
    private SettleStatus settleStatus;

    /**
     * 通用券优惠总额
     */
    @Schema(description = "通用券优惠总额")
    private BigDecimal commonCouponPrice;

    /**
     * 积分抵扣总额
     */
    @Schema(description = "积分抵扣总额")
    private BigDecimal pointPrice;

    /**
     * 积分数总额
     */
    @Schema(description = "积分数总额")
    private Long points;

    /**
     * 分销佣金总额
     */
    @Schema(description = "分销佣金总额")
    private BigDecimal commissionPrice;

    /**
     * 商品实付总额
     */
    @Schema(description = "商品实付总额")
    private BigDecimal splitPayPrice;

    /**
     * 结算时间
     */
    @Schema(description = "结算单创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime settleTime;

    /**
     * 供货价总额
     */
    @Schema(description = "供货价总额")
    private BigDecimal providerPrice;

    /**
     * 总供货运费
     */
    @Schema(description = "总供货运费")
    private BigDecimal thirdPlatFormFreight = BigDecimal.ZERO.setScale(2);

    /**
     * 礼品卡-现金卡抵扣金额
     */
    @Schema(description = "礼品卡-现金卡抵扣金额")
    private BigDecimal giftCardPrice = BigDecimal.ZERO.setScale(2);

    /**
     * 礼品卡-提货卡抵扣金额
     */
    @Schema(description = "礼品卡-提货卡抵扣金额")
    private BigDecimal pickupGiftCardPrice = BigDecimal.ZERO.setScale(2);

    @Schema(description = "社区团购佣金")
    private BigDecimal communityCommissionPrice = BigDecimal.ZERO.setScale(2);
}
