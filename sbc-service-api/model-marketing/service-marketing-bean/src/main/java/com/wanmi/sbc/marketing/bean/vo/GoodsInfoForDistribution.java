package com.wanmi.sbc.marketing.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author : baijz
 * @Date : 2019/2/26 14 14
 * @Description : 分销记录使用到的货品信息
 */
@Schema
@Data
public class GoodsInfoForDistribution implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品SKU编号")
    private String goodsInfoId;

    @Schema(description = "商品编号")
    private String goodsId;

    @Schema(description = "商品SKU名称")
    private String goodsInfoName;

    @Schema(description = "商品SKU编码")
    private String goodsInfoNo;

    @Schema(description = "商品图片")
    private String goodsInfoImg;

    @Schema(description = "商品条形码")
    private String goodsInfoBarcode;

    @Schema(description = "规格名称规格值,颜色:红色;大小:16G")
    private String specText;

    @Schema(description = "分销佣金")
    private BigDecimal distributionCommission;
}
