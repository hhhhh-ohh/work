package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 发货清单
 * @author wumeng[OF2627]
 *         company qianmi.com
 *         Date 2017-04-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ShippingItemDTO implements Serializable {

    /**
     * 清单编号
     */
    @Schema(description = "清单编号")
    private String listNo;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String itemName;

    /**
     * 货号
     */
    @Schema(description = "货号")
    private String bn;

    /**
     * 发货数量
     */
    @Schema(description = "发货数量", required = true)
    @NotNull
    @Min(1L)
    private Long itemNum;

    /**
     * 商品单号
     */
    @Schema(description = "商品单号")
    private String oid;

    @Schema(description = "spu Id")
    private String spuId;

    @Schema(description = "sku Id", required = true)
    @NotNull
    private String skuId;

    @Schema(description = "sku 编号")
    private String skuNo;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String pic;

    /**
     * 规格描述信息
     */
    @Schema(description = "规格描述信息")
    private String specDetails;

    /**
     * 单位
     */
    @Schema(description = "单位")
    private String unit;

    /**
     * 供应商商品编码
     */
    @Schema(description = "供应商商品编码")
    private String providerSkuNo;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;

    @Schema(description = "加价购商品活动ID")
    private Long marketingId;

}
