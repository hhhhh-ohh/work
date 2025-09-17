package com.wanmi.sbc.order.trade.model.entity.value;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ShippingItem {
    /**
     * 清单编号
     */
    private String listNo;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 货号
     */
    private String bn;

    /**
     * 发货数量
     */
    @NotNull
    @Min(1L)
    private Long itemNum;

    /**
     * 商品单号
     */
    private String oid;

    private String spuId;

    @NotNull
    private String skuId;

    private String skuNo;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 规格描述信息
     */
    private String specDetails;

    /**
     * 单位
     */
    private String unit;

    /**
     * 供应商商品编码
     */
    private String providerSkuNo;


    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;

    /**
     * 加价购活动ID
     */
    private Long marketingId;

}
