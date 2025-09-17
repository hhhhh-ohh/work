package com.wanmi.sbc.vas.bean.dto.channel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 渠道订单DTO 与ThirdPlatformTradeVO保持一致
 * @author daiyitian
 * @date 2021/5/11 11:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ChannelOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 子订单id
     */
    @Schema(description = "子订单id")
    private String id;

    /**
     * 供应商订单id
     */
    @Schema(description = "供应商订单id")
    private String parentId;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String tradeId;

    /**
     * 订单组号
     */
    @Schema(description = "订单组号")
    private String groupId;

    /**
     * 购买人
     */
    @Schema(description = "购买人")
    private ChannelBuyerDTO buyer;

    /**
     * 买家备注
     */
    @Schema(description = "买家备注")
    private String buyerRemark;

    /**
     * 卖家备注
     */
    @Schema(description = "卖家备注")
    private String sellerRemark;

    /**
     * 订单附件，以逗号隔开
     */
    @Schema(description = "订单附件,以逗号隔开")
    private String encloses;

    /**
     * 调用方的请求 IP
     * added by shenchunnan
     */
    @Schema(description = "调用方的请求 IP")
    private String requestIp;

    /**
     * 订单总体状态
     */
    @Schema(description = "订单总体状态")
    private ChannelOrderStateDTO tradeState;

    /**
     * 收货人信息
     */
    @Schema(description = "收货人信息")
    private ChannelConsigneeDTO consignee;

    /**
     * 订单价格
     */
    @Schema(description = "渠道价格")
    private ChannelOrderPriceDTO tradePrice;

    /**
     * 订单商品列表
     */
    @NotEmpty
    @Schema(description = "订单商品列表")
    private List<ChannelOrderItemDTO> tradeItems;

    /**
     * 支付单ID
     */
    @Schema(description = "支付单ID")
    private String payOrderId;

    /**
     * 尾款支付单ID
     */
    @Schema(description = "尾款支付单ID")
    private String tailPayOrderId;

    /**
     * 订单来源方
     */
    @Schema(description = "订单来源方")
    private Platform platform;

    /**
     * 订单所属第三方平台类型
     */
    @Schema(description = "订单所属第三方平台类型")
    private ThirdPlatformType thirdPlatformType;

    /**
     * 订单包含第三方平台类型
     */
    private List<ThirdPlatformType> thirdPlatformTypes;

    /**
     * 订单所属第三方平台的订单id
     */
    @Schema(description = "订单所属第三方平台的订单id")
    private List<String> thirdPlatformOrderIds;

    /**
     * 第三方供应商运费
     */
    @Schema(description = "第三方供应商运费")
    private BigDecimal thirdPlatFormFreight;

    /**
     * 外部订单id
     * linkedMall -> 淘宝订单号
     * jd -> 京东订单号
     */
    @Schema(description = "外部订单id")
    private List<String> outOrderIds;

    @Schema(description = "第三方省份code")
    private String thirdProvinceCode;

    @Schema(description = "第三方城市code")
    private String thirdCityCode;

    @Schema(description = "第三方区县code")
    private String thirdDivisionCode;

    @Schema(description = "第三方街道code")
    private String thirdStreetCode;

    /**
     * 下单时是否已开启订单自动审核
     */
    @Schema(description = "下单时是否已开启订单自动审核", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean isAuditOpen = Boolean.TRUE;

    /**
     * 超时未支付取消订单时间
     */
    @Schema(description = "超时未支付取消订单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderTimeOut;

    /**
     * 是否被结算
     */
    @Schema(description = "是否被结算",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean hasBeanSettled;

    /**
     * 是否可退标识
     */
    @Schema(description = "是否可退标识",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean canReturnFlag;

    /**
     * 退款标识
     * 仅供结算使用 - 标识该订单是未收货的退款单子
     * <p>
     * 该单子flowState是作废不会入账，但是退单是COMPLETE状态会入账，导致收支不公，加了单独的状态作为判断
     */
    @Schema(description = "退款标识", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean refundFlag;


    /**
     * 营销赠品全量列表
     */
    @Schema(description = "营销赠品全量列表")
    private List<ChannelOrderItemDTO> gifts;

    @Schema(description = "营销加价购全量列表")
    private List<ChannelOrderItemDTO> preferential = new ArrayList<>();

    /**
     * 是否拼团订单
     */
    @Schema(description = "是否拼团订单")
    private boolean grouponFlag;

    /**
     * 分销渠道类型
     */
    @Schema(description = "分销渠道类型")
    private ChannelType channelType;

    /**
     * 小B店铺内分享链接携带的邀请人ID（会员ID）
     */
    private String distributionShareCustomerId;

    /**
     * 返利人分销员id
     */
    @Schema(description = "返利人分销员id")
    private String distributorId;

    /**
     * 返利人会员id
     */
    @Schema(description = "返利人会员id")
    private String inviteeId;

    /**
     * 小店名称
     */
    @Schema(description = "小店名称")
    private String shopName;

    /**
     * 返利人名称
     */
    @Schema(description = "返利人名称")
    private String distributorName;

    /**
     * 开店礼包
     */
    @Schema(description = "开店礼包")
    private DefaultFlag storeBagsFlag;

    /**
     * 是否组合套装
     */
    private Boolean suitMarketingFlag;

    /**
     * 开店礼包邀请人id
     */
    @Schema(description = "开店礼包邀请人id")
    private String storeBagsInviteeId;

    /**
     * 返利人佣金
     */
    @Schema(description = "返利人佣金")
    private BigDecimal commission;

    /**
     * 总佣金(返利人佣金 + 提成人佣金)
     */
    @Schema(description = "总佣金(返利人佣金 + 提成人佣金)")
    private BigDecimal totalCommission;

    /**
     * 是否返利
     */
    @Schema(description = "是否返利")
    private Boolean commissionFlag;

    /**
     * 正在进行的退单数量
     */
    @Schema(description = "正在进行的退单数量")
    private Integer returnOrderNum;

    /**
     * 可退积分
     */
    @Schema(description = "可退积分")
    private Long canReturnPoints;

    /**
     * 已退金额
     */
    @Schema(description = "已退金额")
    private BigDecimal canReturnPrice;

    /**
     * 订单类型 0：普通订单；1：积分订单；
     */
    @Schema(description = "订单类型 0：普通订单；1：积分订单")
    private OrderType orderType;

    /**
     * 积分订单类型 0：积分商品 1：积分优惠券
     */
    @Schema(description = "积分订单类型 0：积分商品 1：积分优惠券")
    private PointsOrderType pointsOrderType;

    /**
     * 分享人id
     */
    @Schema(description = "分享人id")
    private String shareUserId;

    /**
     * 是否是秒杀抢购商品订单
     */
    @Schema(description = "是否是秒杀抢购商品订单")
    private Boolean isFlashSaleGoods;

    /**
     * 所属商户id-供应商使用
     */
    @Schema(description = "所属商户id-供应商使用")
    private Long storeId;

    /**
     * 所属商户名称-供应商使用
     */
    @Schema(description = "所属商户名称-供应商使用")
    private String supplierName;

    /**
     * 所属商户编号-供应商使用
     */
    @Schema(description = "所属商户编号-供应商使用")
    private String supplierCode;


    /**
     * 是否是预售商品
     */
    @Schema(description = "是否是预售商品")
    private Boolean isBookingSaleGoods;

    /**
     * 尾款通知手机号
     */
    @Schema(description = "尾款通知手机号")
    private String tailNoticeMobile;


    /**
     * 尾款订单号
     */
    @Schema(description = "尾款订单号")
    private String tailOrderNo;

    /**
     * 需要授信还款
     */
    @Schema(description = "需要授信还款")
    private Boolean needCreditRepayFlag;

    /**
     * 第三方内部卖家名称
     */
    @Schema(description = "第三方内部卖家名称")
    private String thirdSellerName;

    /**
     * 第三方内部卖家编号
     */
    @Schema(description = "第三方内部卖家编号")
    private String thirdSellerId;

    /**
     * 第三方平台支付失败状态  true:失败 false:成功
     */
    @Schema(description = "第三方平台支付失败状态")
    private Boolean thirdPlatformPayErrorFlag;
}
