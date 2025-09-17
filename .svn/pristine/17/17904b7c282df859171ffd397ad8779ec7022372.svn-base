package com.wanmi.sbc.order.settlement.model.value;

import com.wanmi.sbc.account.bean.enums.ReturnStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hht on 2017/12/7.
 */
@Data
public class LakalaSettleGood extends SettleGood{

    private String distributorCustomerId;

    private String distributorName;

    private String providerCompanyInfoId;

    private String providerName;

    public LakalaSettleGood(String goodsName, String skuNo, String specDetails, BigDecimal goodsPrice,
                            String cateName, BigDecimal cateRate, long num, BigDecimal splitPayPrice, Long returnNum,
                            Long canReturnNum, BigDecimal shouldReturnPrice, String skuId, ReturnStatus returnStatus,
                            List<MarketingSettlement> marketingSettlements, List<CouponSettlement> couponSettlements,
                            BigDecimal pointPrice, Long points, BigDecimal commissionRate, BigDecimal commission,
                            BigDecimal providerPrice, BigDecimal supplyPrice, String distributorCustomerId,
                            String distributorName, String providerCompanyInfoId, String providerName,
                            Integer goodsSource, BigDecimal giftCardPrice, BigDecimal communityCommission,
                            BigDecimal communityCommissionRate) {
        super(goodsName, skuNo, specDetails, goodsPrice, cateName, cateRate, num, splitPayPrice, returnNum,
                canReturnNum, shouldReturnPrice, skuId, returnStatus, marketingSettlements, couponSettlements,
                pointPrice, points, commissionRate, commission, providerPrice, supplyPrice, goodsSource,
                giftCardPrice, null, communityCommission, communityCommissionRate);
        this.distributorCustomerId = distributorCustomerId;
        this.distributorName = distributorName;
        this.providerCompanyInfoId = providerCompanyInfoId;
        this.providerName = providerName;
    }

    public LakalaSettleGood() {
    }
}
