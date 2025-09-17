package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import com.wanmi.sbc.marketing.bean.vo.TradeCouponVO;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO;
import com.wanmi.sbc.order.bean.enums.EvaluateStatus;
import com.wanmi.sbc.order.bean.enums.OrderSource;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;
import com.wanmi.sbc.order.bean.vo.FullReturnCouponVO;
import com.wanmi.sbc.order.bean.vo.OrderTagVO;
import com.wanmi.sbc.order.bean.vo.PickSettingInfoVO;
import com.wanmi.sbc.order.bean.vo.WriteOffInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String id;

    /**
     * 父订单号
     */
    @Schema(description = "父订单号")
    private String parentId;

    /**
     * 主订单号
     */
    @Schema(description = "主订单号")
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
    private BuyerDTO buyer;

    /**
     * boss卖方
     */
    @Schema(description = "boss卖方")
    private SellerDTO seller;

    /**
     * 商家
     */
    @Schema(description = "商家")
    private SupplierDTO supplier;

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
     * 发票
     */
    @Schema(description = "发票")
    private InvoiceDTO invoice;

    /**
     * 订单总体状态
     */
    @Schema(description = "订单总体状态")
    private TradeStateDTO tradeState;

    /**
     * 收货人信息
     */
    @Schema(description = "收货人信息")
    private ConsigneeDTO consignee;

    /**
     * 订单价格
     */
    @Schema(description = "订单价格")
    private TradePriceDTO tradePrice;

    /**
     * 订单商品列表
     */
    @Schema(description = "订单商品列表")
    private List<TradeItemDTO> tradeItems = new ArrayList<>();

    /**
     * 发货单
     */
    @Schema(description = "发货单")
    private List<TradeDeliverDTO> tradeDelivers = new ArrayList<>();

    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
    private DeliverWay deliverWay;

    @Schema(description = "订单支付信息")
    private PayInfoDTO payInfo;

    /**
     * 支付单ID
     */
    @Schema(description = "支付单ID")
    private String payOrderId;

    /**
     * 尾款支付单ID
     */
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
     * 第三方平台支付失败状态  true:失败 false:成功
     */
    @Schema(description = "第三方平台支付失败状态")
    private Boolean thirdPlatformPayErrorFlag;

    /**
     * 外部订单id
     * linkedMall -> 淘宝订单号
     */
	@Schema(description = "外部订单id")
    private List<String> outOrderIds;

    /**
     * 第三方省份code
     */
	@Schema(description = "第三方省份code")
    private String thirdProvinceCode;

    /**
     * 第三方城市code
     */
	@Schema(description = "第三方城市code")
    private String thirdCityCode;

    /**
     * 第三方区县code - linkedMall需要
     */
	@Schema(description = "第三方区县code")
    private String thirdDivisionCode;

    /**
     * 第三方街道code
     */
	@Schema(description = "第三方街道code")
    private String thirdStreetCode;

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
     * 下单时是否已开启订单自动审核
     */
    @Schema(description = "下单时是否已开启订单自动审核", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean isAuditOpen = Boolean.TRUE;

    /**
     * 订单支付顺序
     */
    @Schema(description = "订单支付顺序")
    private PaymentOrder paymentOrder;

    /**
     * 超时未支付取消订单时间
     */
    @Schema(description = "超时未支付取消订单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderTimeOut;

    /**
     * 操作日志记录（状态变更）
     */
    @Schema(description = "操作日志记录，状态变更")
    private List<TradeEventLogDTO> tradeEventLogs = new ArrayList<>();

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
     * 订单营销信息
     */
    @Schema(description = "订单营销信息")
    private List<TradeMarketingVO> tradeMarketings;

    /**
     * 订单使用的店铺优惠券
     */
    @Schema(description = "订单使用的店铺优惠券")
    private TradeCouponVO tradeCoupon;

    /**
     * 运费优惠券信息
     */
    private TradeCouponVO freightCoupon;

    /**
     * 营销赠品全量列表
     */
    @Schema(description = "营销赠品全量列表")
    private List<TradeItemDTO> gifts = new ArrayList<>();

    @Schema(description = "营销加价购商品全量列表")
    private List<TradeItemDTO> preferential = new ArrayList<>();

    /**
     * 订单来源--区分h5,pc,app,小程序,代客下单
     */
    @Schema(description = "订单来源")
    private OrderSource orderSource;

    /**
     * 是否拼团订单
     */
    @Schema(description = "是否拼团订单")
    private boolean grouponFlag;

    /**
     * 订单拼团信息
     */
    @Schema(description = "订单拼团信息")
    private TradeGrouponDTO tradeGroupon;

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
     * 分销单品列表
     */
    @Schema(description = "分销单品列表")
    private List<TradeDistributeItemDTO> distributeItems = new ArrayList<>();

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
     * 提成人佣金列表
     */
    @Schema(description = "提成人佣金列表")
    private List<TradeCommissionDTO> commissions = new ArrayList<>();

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
     * 订单评价状态
     */
    @Schema(description = "订单评价状态")
    private EvaluateStatus orderEvaluateStatus;

    /**
     * 店铺服务评价状态
     */
    @Schema(description = "店铺服务评价状态")
    private EvaluateStatus storeEvaluate;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private PayWay payWay;

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
     * 是否是限时抢购商品订单
     */
    @Schema(description = "是否是限时抢购商品订单")
    private Boolean isFlashPromotionGoods;

    /**
     * 所属商户id-供应商使用
     */
    private Long storeId;

    /**
     * 所属商户名称-供应商使用
     */
    private String supplierName;

    /**
     * 所属商户编号-供应商使用
     */
    private String supplierCode;


    /**
     * 是否是预售商品
     */
    private Boolean isBookingSaleGoods;

    /**
     * 尾款通知手机号
     */
    private String tailNoticeMobile;

    /**
     * 预售类型
     */
    private BookingType bookingType;


    /**
     * 尾款订单号
     */
    private String tailOrderNo;

    /**
     * 授信支付相关信息
     */
    @Schema(description = "授信支付相关信息")
    private CreditPayInfoDTO creditPayInfo;

    /**
     * 需要授信还款
     */
    @Schema(description = "需要授信还款")
    private Boolean needCreditRepayFlag;
    /**
     * 跨境订单的扩展字段
     */
    @Schema(description = "跨境订单的扩展字段")
    private Object extendedAttributes;
    /**
     * 是否跨境订单
     */
    @Schema(description = "是否跨境订单")
    private Boolean crossBorderFlag = false;

    /**
     * 是否第三次拆单
     */
    @Schema(description = "是否跨境订单")
    private Boolean splitOrderFlag = false;

    /**
     * 是否自提订单
     */
    @Schema(description = "是否自提订单")
    private Boolean pickupFlag = false;

    /**
     * 自提信息
     */
    @Schema(description = "自提信息")
    private PickSettingInfoVO pickSettingInfo;

    /**
     * 核销信息
     */
    @Schema(description = "核销信息")
    private WriteOffInfoVO writeOffInfo;

    /**
     * 运费信息
     */
    @Schema(description = "运费信息")
    private FreightDTO freight;

    /**
     * 是否允许在途退货 TRUE 允许 FALSE 不允许
     */
    private Boolean transitReturn = Boolean.FALSE;

    /**
     * 订单标记
     */
    @Schema(description = "订单标记")
    private OrderTagVO orderTag;

    /**
     * 卡密信息
     */
    @Schema(description = "卡密信息")
    private List<ElectronicCardVO> electronicCards;

    /**
     * 满返发券信息
     */
    @Schema(description = "满返发券信息")
    private List<FullReturnCouponVO> fullReturnCoupons = new ArrayList<>();

    /**
     * 是否发券
     */
    @Schema(description = "是否发券")
    private Boolean sendCouponFlag = false;

    /**
     * 代销平台标识
     */
    @Schema(description = "代销平台标识")
    private SellPlatformType sellPlatformType;

    /**
     * 代销平台的订单ID
     */
    @Schema(description = "代销平台的订单ID")
    private String sellPlatformOrderId;

    /**
     * 场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）
     */
    @Schema(description = "场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）")
    private Integer sceneGroup;

    /**
     * 付费会员信息
     */
    @Schema(description = "付费会员信息")
    private PayingMemberInfoDTO payingMemberInfo;

    /**
     * 是否是砍价订单
     */
    @Schema(description = "是否是砍价订单")
    private Boolean bargain;

    /**
     * 砍价记录订单
     */
    @Schema(description = "砍价记录订单")
    private Long bargainId;

    /**
     * 支付版本号
     * 默认10，每次拉起支付后加1
     */
    @Schema(description = "支付版本号")
    private int payVersion;


    /**
     * 周期购信息
     */
    @Schema(description = "周期购信息")
    private TradeBuyCycleDTO tradeBuyCycle;

    /**
     * 标识买家是否已修改过收货地址
     */
    @Schema(description = "标识买家是否已修改过收货地址")
    private Boolean buyerHasModifiedConsignee;

    /**
     * 社区团购
     */
    @Schema(description = "社区团购")
    private CommunityTradeCommissionDTO communityTradeCommission;

    /**
     * 拉卡拉订单交易信息
     */
    @Schema(description = "拉卡拉订单交易信息")
    private LklOrderTradeInfoDTO lklOrderTradeInfo;
}
