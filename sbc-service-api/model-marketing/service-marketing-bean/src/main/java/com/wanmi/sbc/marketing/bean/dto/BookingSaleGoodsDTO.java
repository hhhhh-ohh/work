package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>预售商品信息DTO</p>
 *
 * @author dany
 * @date 2020-06-05 10:51:35
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSaleGoodsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 预售id
     */
    @Schema(description = "预售id")
    private Long bookingSaleId;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    private Long storeId;

    /**
     * skuID
     */
    @Schema(description = "skuID")
    private String goodsInfoId;

    /**
     * spuID
     */
    @Schema(description = "spuID")
    private String goodsId;

    /**
     * 定金
     */
    @Schema(description = "定金")
    private BigDecimal handSelPrice;

    /**
     * 膨胀价格
     */
    @Schema(description = "膨胀价格")
    private BigDecimal inflationPrice;

    /**
     * 预售价
     */
    @DecimalMin(value = "0", message = "预售价仅限0-9999999.99")
    @DecimalMax(value = "9999999.99", message = "预售价仅限0-9999999.99")
    @Schema(description = "预售价")
    private BigDecimal bookingPrice;

    /**
     * 预售数量
     */
    @Min(value = 1L, message = "预售数量必须大于0")
    @Schema(description = "预售数量")
    private Integer bookingCount;

    /**
     * 定金支付数量
     */
    @Schema(description = "定金支付数量")
    private Integer handSelCount;

    /**
     * 尾款支付数量
     */
    @Schema(description = "尾款支付数量")
    private Integer tailCount;

    /**
     * 全款支付数量
     */
    @Schema(description = "全款支付数量")
    private Integer payCount;

    /**
     * 预售库存
     */
    @Schema(description = "预售库存")
    private Integer stock;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "goodsType")
    private Integer goodsType;

}
