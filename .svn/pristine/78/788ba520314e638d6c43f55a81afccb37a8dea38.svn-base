package com.wanmi.ares.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class MarketingOverview extends MarketingAnalysisBase implements Serializable {

    @Schema(description = "时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate date;

    @Schema(description = "优惠券活动数量")
    private long couponCount;

    @Schema(description = "拼团活动数量")
    private long grouponCount;

    @Schema(description = "秒杀活动数量")
    private long flashSaleCount;

    @Schema(description = "满系活动数量")
    private long fullSubtractDiscountCount;

    @Schema(description = "预售活动数量")
    private long bookingSaleCount;

    @Schema(description = "预售活动数量")
    private long appointmentCount;

    @Schema(description = "营销支付金额占比")
    private BigDecimal payMoneyRate;

    @Schema(description = "非营销支付金额")
    private BigDecimal noMarketingPayMoney;

    @Schema(description = "支付总金额")
    private BigDecimal totalPayMoney;

    @Schema(description = "打包一口价活动数量")
    private long buyoutPriceCount;

    @Schema(description = "第二件半价活动数量")
    private long halfPriceSecondPieceCount;

    @Schema(description = "组合购活动数量")
    private long suitsCount;

    @Schema(description = "砍价活动数量")
    private long bargainCount;

    @Schema(description = "加价购活动数量")
    private long preferentialCount;

    @Override
    public String toString() {
        return "MarketingOverview{" +
                "couponCount=" + couponCount +
                ", grouponCount=" + grouponCount +
                ", flashSaleCount=" + flashSaleCount +
                ", fullSubtractDiscountCount=" + fullSubtractDiscountCount +
                ", bookingSaleCount=" + bookingSaleCount +
                ", appointmentCount=" + appointmentCount +
                ", payMoneyRate=" + payMoneyRate +
                ", noMarketingPayMoney=" + noMarketingPayMoney +
                ", totalPayMoney=" + totalPayMoney +
                ", buyoutPriceCount=" + buyoutPriceCount +
                ", halfPriceSecondPieceCount=" + halfPriceSecondPieceCount +
                ", suitsCount=" + suitsCount +
                ", bargainCount=" + bargainCount +
                ", preferentialCount=" + preferentialCount +
                "} " + super.toString();
    }
}
