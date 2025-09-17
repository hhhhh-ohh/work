package com.wanmi.ares.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class FullMoneyBookingReport extends MarketingAnalysisBase {

    // 商品名称
    @Schema(description = "商品名称")
    private String goodsInfoName;

    // 商品编码
    @Schema(description = "商品编码")
    private String goodsInfoNo;

    @Schema(description = "sku_id")
    private String goodsInfoId;

    @Schema(description = "规格")
    private String specDetails;

    @Override
    public String toString() {
        return "FullMoneyBookingReport{" +
                "goodsInfoName='" + goodsInfoName + '\'' +
                ", goodsInfoNo='" + goodsInfoNo + '\'' +
                ", goodsInfoId='" + goodsInfoId + '\'' +
                ", specDetails='" + specDetails + '\'' +
                "} " + super.toString();
    }
}
