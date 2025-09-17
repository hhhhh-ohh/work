package com.wanmi.ares.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema
@EqualsAndHashCode(callSuper = true)
public class FlashSaleGoods extends MarketingAnalysisBase implements Serializable {

    /**
     * sku id
     */
    @Schema(description = "sku id")
    private String goodsInfoId;

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
     * 规格信息
     */
    @Schema(description = "规格信息")
    private String specDetails;
}
