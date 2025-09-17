package com.wanmi.sbc.order.settlement.model.value;

import com.wanmi.sbc.account.bean.enums.GatherType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.enums.TradeType;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.order.trade.model.root.TradeCommission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hht on 2017/12/6.
 */
@Data
public class LakalaSettleTrade extends SettleTrade {

    /**
     * 商家编码
     */
    @Schema(description = "商家编码")
    private String supplierCode;

    /**
     * 商家id
     */
    @Schema(description = "商家ID")
    private Long supplierId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    @Schema(description = "父订单ID")
    private String parentId;

    @Schema(description = "供应商分账金额")
    private BigDecimal totalProviderPrice = BigDecimal.ZERO;

    @Schema(description = "分销员分账金额")
    private BigDecimal totalCommissionPrice = BigDecimal.ZERO;

    @Schema(description = "平台分账金额")
    private BigDecimal totalPlatformPrice = BigDecimal.ZERO;

    @Schema(description = "分销返利人佣金")
    private BigDecimal commission = BigDecimal.ZERO;

    @Schema(description = "分销提成人佣金")
    private List<TradeCommission> commissions = new ArrayList<>();

    /**
     * 拉卡拉分配的商户号
     */
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    /**
     * 终端号
     */
    @Schema(description = "终端号")
    private String termNo;

    public LakalaSettleTrade(LocalDateTime tradePayTime, LocalDateTime tradeCreateTime, LocalDateTime tradeEndTime,
                             LocalDateTime finalTime, String tradeCode, TradeType tradeType, OrderType orderType,
                             GatherType gatherType, BigDecimal deliveryPrice, BigDecimal returnPrice,
                             BigDecimal salePrice, BigDecimal returnSpecialPrice, BigDecimal storePrice,
                             BigDecimal providerPrice, BigDecimal thirdPlatFormFreight, Long points, PayWay payWay,
                             BigDecimal providerReturnSpecialPrice, String supplierCode, Long supplierId,
                             Long storeId, String parentId, BigDecimal totalProviderPrice,
                             BigDecimal totalCommissionPrice, BigDecimal totalPlatformPrice, BigDecimal commission,
                             List<TradeCommission> commissions,BigDecimal freightCouponPrice, BigDecimal giftCardPrice,
                             String merchantNo, String termNo) {
        super(tradePayTime, tradeCreateTime, tradeEndTime, finalTime, tradeCode, tradeType, orderType, gatherType,
                deliveryPrice, returnPrice, salePrice, returnSpecialPrice, storePrice, providerPrice,
                thirdPlatFormFreight, points, payWay, providerReturnSpecialPrice,freightCouponPrice, giftCardPrice, null);
        this.supplierCode = supplierCode;
        this.supplierId = supplierId;
        this.storeId = storeId;
        this.parentId = parentId;
        this.totalProviderPrice = totalProviderPrice;
        this.totalCommissionPrice = totalCommissionPrice;
        this.totalPlatformPrice = totalPlatformPrice;
        this.commission = commission;
        this.commissions = commissions;
        this.merchantNo = merchantNo;
        this.termNo = termNo;
    }

    public LakalaSettleTrade() {
    }
}
