package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.goods.bean.enums.SaleType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>SKU市场价信息</p>
 * Created by of628-wenzhi on 2020-12-14-9:33 下午.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class GoodsInfoMarketingPriceDTO implements Serializable {
    private static final long serialVersionUID = 8951613639953363518L;

    /**
     * SKU ID
     */
    @Schema(description = "SKU ID")
    private String goodsInfoId;

    /**
     * SKU NO
     */
    @Schema(description = "SKU NO")
    private String goodsInfoNo;

    /**
     * SKU 名称
     */
    @Schema(description = "SKU 名称")
    private String goodsInfoName;

    /**
     * SPU ID
     */
    private String goodsId;

    /**
     * 规格值明细，以空格间隔，例：红色 16G
     */
    @Schema(description = "规格值明细，以空格间隔，例：红色 16G")
    private String specText;

    /**
     * 销售类型
     */
    @Schema(description = "销售类型")
    private SaleType saleType;

    /**
     * 市场价
     */
    @Schema(description = "市场价")
    private BigDecimal marketingPrice;

    /**
     * 供货价
     */
    @Schema(description = "供货价")
    private BigDecimal supplyPrice;

    @Schema(description = "供应商商品ID")
    private String providerGoodsInfoId;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0:实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    private BigDecimal linePrice;
}
