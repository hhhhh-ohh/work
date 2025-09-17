package com.wanmi.sbc.account.api.request.finance.record;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LakalaSettlementGetViewRequest extends BaseRequest {
    @Schema(description = "结算单id")
    private Long settleId;

    @Schema(description = "结算单创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "结算单开始时间")
    private String startTime;

    @Schema(description = "结算单结束时间")
    private String endTime;

    @Schema(description = "商家Id")
    private Long storeId;

    // totalProviderPrice + totalStorePrice + commissionPrice + platformPrice
    @Schema(description = "分账总金额")
    private BigDecimal totalAmount;

    // providerGoodsTotalPrice + providerDeliveryTotalPrice
    @Schema(description = "供应商分账总额")
    private BigDecimal totalProviderPrice;

    @Schema(description = "店铺分账总额")
    private BigDecimal totalStorePrice;

    @Schema(description = "分销佣金分账总额")
    private BigDecimal totalCommissionPrice;

    @Schema(description = "平台佣金分账总额")
    private BigDecimal totalPlatformPrice;

    @Schema(description = "商品实付总额")
    private BigDecimal totalSplitPayPrice;

    @Schema(description = "通用券优惠总额")
    private BigDecimal totalCommonCouponPrice;

    @Schema(description = "积分数量")
    private Long totalPoints;

    @Schema(description = "积分抵扣总额")
    private BigDecimal pointPrice;

    @Schema(description = "商品供货总额")
    private BigDecimal providerGoodsTotalPrice;

    @Schema(description = "商品供货运费总额")
    private BigDecimal providerDeliveryTotalPrice;

    @Schema(description = "总运费")
    private BigDecimal deliveryPrice;

    @Schema(description = "分账状态")
    private LakalaLedgerStatus lakalaLedgerStatus;

    @Schema(description = "礼品卡抵扣金额")
    private BigDecimal giftCardPrice;

}
