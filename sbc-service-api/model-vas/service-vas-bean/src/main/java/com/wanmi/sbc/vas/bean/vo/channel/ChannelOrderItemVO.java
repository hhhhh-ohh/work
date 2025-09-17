package com.wanmi.sbc.vas.bean.vo.channel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description 渠道订单明细DTO 与TradeItemVO保持一致
 * @author daiyitian
 * @date 2021/5/11 11:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ChannelOrderItemVO implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单标识")
    private String oid;

    @Schema(description = "商品所属的userId")
    private String adminId;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "商家编码")
    private String supplierCode;

    @Schema(description = "spu Id")
    private String spuId;

    @Schema(description = "spu 名称")
    private String spuName;

    @Schema(description = "sku Id")
    private String skuId;

    @Schema(description = "sku 名字")
    private String skuName;

    @Schema(description = "sku 编码")
    private String skuNo;

    @Schema(description = "第三方平台的spuId")
    private String thirdPlatformSpuId;

    @Schema(description = "第三方平台的skuId")
    private String thirdPlatformSkuId;

    @Schema(description = "第三方平台类型，0，linkedmall")
    private ThirdPlatformType thirdPlatformType;

    @Schema(description = "第三方平台商品对应的子单号")
    private String thirdPlatformSubOrderId;

    @Schema(description = "商品来源，0供应商，1商家 2linkedMall")
    private Integer goodsSource;

    @Schema(description = "商品重量")
    private BigDecimal goodsWeight;

    @Schema(description = "商品体积，单位：m3")
    private BigDecimal goodsCubage;

    @Schema(description = "运费模板ID")
    private Long freightTempId;

    @Schema(description = "顶级分类id")
    private Long cateTopId;

    @Schema(description = "分类")
    private Long cateId;

    @Schema(description = "分类名称")
    private String cateName;

    @Schema(description = "商品图片")
    private String pic;

    @Schema(description = "商品品牌")
    private Long brand;

    @Schema(description = "购买数量")
    @Min(1L)
    private Long num;

    @Schema(description = "已发货数量")
    private Long deliveredNum;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "成交价格")
    private BigDecimal price;

    @Schema(description = "定金")
    private BigDecimal earnestPrice;

    @Schema(description = "定金膨胀")
    private BigDecimal swellPrice;

    @Schema(description = "尾款")
    private BigDecimal tailPrice;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime handSelStartTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime handSelEndTime;

    @Schema(description = "尾款支付开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tailStartTime;

    @Schema(description = "尾款支付结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tailEndTime;

    @Schema(description = "商品原价 建议零售价")
    private BigDecimal originalPrice;

    @Schema(description = "商品价格 会员价 & 阶梯设价")
    private BigDecimal levelPrice;

    @Schema(description = "成本价")
    private BigDecimal cost;

    @Schema(description = "平摊小计 最初由 levelPrice*num（购买数量） 计算")
    private BigDecimal splitPrice;

    @Schema(description = "货物id")
    private String bn;

    @Schema(description = "可退数量")
    private Integer canReturnNum;

    @Schema(description = "规格描述信息")
    private String specDetails;

    @Schema(description = "分类扣率")
    private BigDecimal cateRate;

    @Schema(description = "分销佣金（返利人）")
    private BigDecimal distributionCommission;

    private BigDecimal commissionRate;

    @Schema(description = "商品参加的营销活动id集合")
    private List<Long> marketingIds;

    @Schema(description = "商品参加的营销活动levelId集合")
    private List<Long> marketingLevelIds;

    @Schema(description = "积分")
    private Long points;

    @Schema(description = "商品购买积分，被用于普通订单的积分+金额混合商品")
    private Long buyPoint;

    @Schema(description = "积分兑换金额")
    private BigDecimal pointsPrice;

    @Schema(description = "积分商品Id")
    private String pointsGoodsId;

    @Schema(description = "结算价格")
    private BigDecimal settlementPrice;

    @Schema(description = "企业购商品的价格")
    private BigDecimal enterPrisePrice;

    /** 是否是秒杀抢购商品 */
    private Boolean isFlashSaleGoods;

    /** 秒杀抢购商品Id */
    private Long flashSaleGoodsId;

    /** 供应商id */
    private Long providerId;

    /** 供货价 */
    private BigDecimal supplyPrice;

    /** 供货价总额 */
    private BigDecimal totalSupplyPrice;

    /** 供应商名称 */
    private String providerName;

    /** 供应商编号 */
    private String providerCode;

    /** 供应商SKU编码 */
    private String providerSkuNo;

    /** 供应商SKUid */
    private String providerSkuId;

    /** 是否是预约抢购商品 */
    private Boolean isAppointmentSaleGoods;

    /** 抢购活动Id */
    private Long appointmentSaleId;

    /** 是否是预售商品 */
    private Boolean isBookingSaleGoods;

    /** 预售活动Id */
    private Long bookingSaleId;
}
