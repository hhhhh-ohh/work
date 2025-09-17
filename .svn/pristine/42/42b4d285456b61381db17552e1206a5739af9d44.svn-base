package com.wanmi.sbc.goods.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className NewcomerPurchaseGoodsMagicVO
 * @description
 * @date 2022/8/24 10:20 AM
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewcomerPurchaseGoodsMagicVO implements Serializable {

    private static final long serialVersionUID = 5689060439546607401L;

    /**
     * sku ID
     */
    @Schema(description = "sku ID")
    private String goodsInfoId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsInfoName;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型 0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 市场价
     */
    @Schema(description = "市场价")
    private BigDecimal marketPrice;

    /**
     * 券后价
     */
    @Schema(description = "新人券后价")
    private BigDecimal couponPrice;

    /**
     * 商品图片
     */
    @Schema(description = "图片")
    private String goodsInfoImg;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String specText;

    /**
     * 品牌ID
     */
    @Schema(description = "品牌ID")
    private Long brandId;


    /**
     * 商品分类ID
     */
    @Schema(description = "商品分类ID")
    private Long cateId;

    private Long storeId;
}
