package com.wanmi.ares.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DepositBookingReport extends MarketingAnalysisBase {

    // 订金支付人数
    @Schema(description = "订金支付人数")
    private Long payDepositCount;

    // 尾款支付人数
    @Schema(description = "尾款支付人数")
    private Long payTailCount;

    // 转化率
    @Schema(description = "订金-尾款转化率")
    private BigDecimal conversionRates;

    // 商品名称
    @Schema(description = "商品名称")
    private String goodsInfoName;

    // 商品编码
    @Schema(description = "商品编码")
    private String goodsInfoNo;

    // sku_id
    @Schema(description = "sku_id")
    private String goodsInfoId;

    /**
     * 商品规格
     */
    @Schema(description = "商品规格")
    private String specDetails;

    /**
     * 访问-定金转化率
     */
    @Schema(description = "访问-定金转化率")
    private BigDecimal uvDepositRate;

    @Override
    public String toString() {
        return "DepositBookingReport{" +
                "payDepositCount=" + payDepositCount +
                ", payTailCount=" + payTailCount +
                ", conversionRates=" + conversionRates +
                ", goodsInfoName='" + goodsInfoName + '\'' +
                ", goodsInfoNo='" + goodsInfoNo + '\'' +
                ", goodsInfoId='" + goodsInfoId + '\'' +
                ", specDetails='" + specDetails + '\'' +
                ", uvDepositRate='" + uvDepositRate + '\'' +
                "} " + super.toString();
    }
}
