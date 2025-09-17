package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Autho qiaokang
 * @Date：2020-03-30 10:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ProviderTradeExportVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String parentId;

    /**
     * 子订单号
     */
    @Schema(description = "子订单号")
    private String id;

    /**
     * 下单时间
     */
    @Schema(description = "下单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 商家
     */
    @Schema(description = "商家")
    private String supplierInfo;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String consigneeName;

    /**
     * 收货人电话
     */
    @Schema(description = "收货人电话")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String consigneePhone;

    /***
     * 详细地址(包含省市区）
     */
    @Schema(description = "详细地址(包含省市区）")
    @SensitiveWordsField(signType = SignWordType.ADDRESS)
    private String detailAddress;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
    private DeliverWay deliverWay;

    /**
     * 配送方式名称
     */
    @Schema(description = "配送方式名称")
    private String deliverWayName;

    /**
     * 订单商品金额
     */
    @Schema(description = "订单商品金额")
    private BigDecimal orderGoodsPrice;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String skuName;

    /**
     * 商品规格
     */
    @Schema(description = "商品规格")
    private String specDetails;

    /**
     * sku编码
     */
    @Schema(description = "sku编码")
    private String skuNo;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    private Long num;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String buyerRemark;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private FlowState flowState;

    /**
     * 发货状态
     */
    @Schema(description = "发货状态")
    private DeliverStatus deliverStatus;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String supplierName;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态")
    private PayState payState;

    /**
     * 商品总数量
     */
    @Schema(description = "商品总数量")
    private Long totalNum;

    /**
     * 商品种类
     */
    @Schema(description = "商品种类")
    private Long goodsSpecies;

    /**
     * 订单所属第三方平台的订单id
     *
     * linkedmall --> linkedmall订单号
     */
    @Schema(description = "订单所属第三方平台的订单id")
    private String thirdPlatformOrderId;

    /**
     * 第三方平台外部订单id
     * linkedmall --> 淘宝订单号
     */
    @Schema(description = "第三方平台外部订单id，linkedmall --> 淘宝订单号")
    private String outOrderId;

    /**
     * 供货价
     */
    @Schema(description = "供货价")
    private BigDecimal supplyPrice;

    /**
     * 供货商
     */
    @Schema(description = "供货商")
    private String providerName;

    /**
     * 积分
     */
    @Schema(description = "积分")
    private Long points;

    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 积分兑换金额
     */
    @Schema(description = "积分兑换金额")
    private BigDecimal pointsPrice;

    /**
     * 是否拼团订单
     */
    private Boolean grouponFlag;

    /**
     * 是否是秒杀抢购商品订单
     */
    private Boolean isFlashSaleGoods;

    /**
     * 是否是砍价订单
     */
    private Boolean bargain;

    /**
     * 订单标记
     */
    private OrderTagVO orderTag;


    /**
     * 周期购信息
     */
    private TradeBuyCycleVO tradeBuyCycle;

    @Schema(description = "订单商品金额")
    private BigDecimal giftCardPrice;
}
