package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.PluginType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
public class ShippingItemVO extends BasicResponse {
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
    @Schema(description = "发货数量")
    @NotNull
    @Min(1L)
    private Long itemNum;

    /**
     * 商品单号
     */
    @Schema(description = "商品单号")
    private String oid;

    @Schema(description = "spuId")
    private String spuId;

    @Schema(description = "skuId")
    @NotNull
    private String skuId;

    @Schema(description = "skuNo")
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

    @Schema(description = "购买价格")
    private BigDecimal price;

    @Schema(description = "积分价")
    private Long buyPoint;

    /**
     * 供应商商品编码
     */
    @Schema(description = "供应商商品编码")
    private String providerSkuNo;

    /**
     * 积分
     */
    @Schema(description = "积分")
    private Long points;

    /**
     * 商品类型 0 普通商品 1 跨境商品
     */
    @Schema(description = "商品类型 0 普通商品 1 跨境商品")
    private PluginType pluginType;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;

    @Schema(description = "活动ID")
    private Long marketingId;

}
