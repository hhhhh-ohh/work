package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.order.bean.vo.ReturnItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 退货商品类目
 * Created by jinwei on 19/4/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ReturnItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "skuId")
    private String skuId;

    @Schema(description = "sku 名称")
    private String skuName;

    @Schema(description = "sku 编号")
    private String skuNo;

    /**
     * 规格信息
     */
    @Schema(description = "规格信息")
    private String specDetails;

    /**
     * 退货商品单价 = 商品原单价 - 商品优惠单价
     */
    @Schema(description = "退货商品单价 = 商品原单价 - 商品优惠单价")
    private BigDecimal price;

    /**
     * 平摊价格
     */
    @Schema(description = "平摊价格")
    private BigDecimal splitPrice;

    /**
     * 供货价
     */
    @Schema(description = "供货价")
    private BigDecimal supplyPrice;

    /**
     * 供货价小计
     */
    @Schema(description = "供货价小计")
    private BigDecimal providerPrice;

    /**
     * 订单平摊价格
     */
    @Schema(description = "订单平摊价格")
    private BigDecimal orderSplitPrice;

    /**
     * 申请退货数量
     */
    @Schema(description = "申请退货数量")
    private Integer num;


    /**
     * 周期购购买期数
     */
    private Integer buyCycleNum;

    /**
     * 退货商品图片路径
     */
    @Schema(description = "退货商品图片路径")
    private String pic;

    /**
     * 单位
     */
    @Schema(description = "单位")
    private String unit;

    /**
     * 仍可退数量
     */
    @Schema(description = "仍可退数量")
    private Integer canReturnNum;

    /**
     * 购买积分，被用于普通订单的积分+金额混合商品
     */
    @Schema(description = "购买积分")
    private Long buyPoint;



    /**
     * 第三方平台的spuId
     */
    private String thirdPlatformSpuId;

    /**
     * 第三方平台的skuId
     */
    private String thirdPlatformSkuId;

    /**
     * 商品来源，0供应商，1商家 2linkedMall
     */
    private Integer goodsSource;

    /**
     *第三方平台类型，0，linkedmall
     */
    private ThirdPlatformType thirdPlatformType;


    /**
     * 第三方平台-明细子订单id
     */
    private String thirdPlatformSubOrderId;

    /**
     * 供货商id
     */
    private Long providerId;

    /**
     * 应退积分
     */
    @Schema(description = "应退积分")
    private Long splitPoint;
    /**
     * 跨境订单商品的扩展字段
     */
    @Schema(description = "跨境订单商品的扩展字段")
    private Object extendedAttributes;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 周期购退货数量
     */
    @Schema(description = "周期购退货数量")
    private Integer cycleReturnNum;

    @Schema(description = "活动ID")
    private Long marketingId;

    /**
     * 商品退礼品卡详情
     */
    private List<ReturnItemVO.GiftCardItemVO> giftCardItemList = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GiftCardItemVO implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 用户礼品卡Id
         */
        private Long userGiftCardID;

        /**
         * 礼品卡卡号
         */
        private String giftCardNo;

        /**
         * 礼品卡抵扣金额
         */
        private BigDecimal returnPrice;
    }
}
