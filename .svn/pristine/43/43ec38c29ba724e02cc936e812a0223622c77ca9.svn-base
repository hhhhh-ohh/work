package com.wanmi.ares.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class MarketingReduceDiscountGiftReport extends MarketingAnalysisBase implements Serializable {

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsInfoName;

    /**
     * 商品编码
     */
    @Schema(description = "商品编码")
    private String goodsInfoNo;

    /**
     * sku编号
     */
    @Schema(description = "sku_id")
    private String goodsInfoId;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String specDetails;

    @Override
    public String toString() {
        return "MarketingReduceDiscountGiftReport{" +
                "goodsInfoName='" + goodsInfoName + '\'' +
                ", goodsInfoNo='" + goodsInfoNo + '\'' +
                ", goodsInfoId='" + goodsInfoId + '\'' +
                ", specDetails='" + specDetails + '\'' +
                "} " + super.toString();
    }
}
