package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.VideoUserVO;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.order.bean.enums.OrderSource;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单状态
 * Created by jinwei on 14/3/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     *子订单列表
     */
    @Schema(description = "子订单列表")
    private List<TradeVO> tradeVOList;

    /**
     *子订单列表,新增接口的子单列表用这个
     */
    @Schema(description = "子订单列表")
    private List<ProviderTradeVO> providerTradeVOList;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String id;

    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String tradeNo;

    /**
     * 父订单号，用于不同商家订单合并支付场景
     */
    @Schema(description = "父订单号，用于不同商家订单合并支付场景")
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
    private BuyerVO buyer;

    /**
     * boss卖方
     */
    @Schema(description = "boss卖方")
    private SellerVO seller;

    /**
     * 商家
     */
    @Schema(description = "商家")
    private SupplierVO supplier;

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
    @Schema(description = "订单附件，以逗号隔开")
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
    private InvoiceVO invoice;

    /**
     * 订单总体状态
     */
    @Schema(description = "订单总体状态")
    private TradeStateVO tradeState;

    /**
     * 收货人信息
     */
    @Schema(description = "收货人信息")
    private ConsigneeVO consignee;

    /**
     * 订单价格
     */
    @Schema(description = "订单价格")
    private TradePriceVO tradePrice;

    /**
     * 订单商品列表
     */
    @Schema(description = "订单商品列表")
    private List<TradeItemVO> tradeItems = new ArrayList<>();

    /**
     * 发货单
     */
    @Schema(description = "发货单")
    private List<TradeDeliverVO> tradeDelivers = new ArrayList<>();

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

    @Schema(description = "支付信息")
    private PayInfoVO payInfo;

    /**
     * 支付单ID
     */
    @Schema(description = "支付单ID")
    private String payOrderId;

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
     */
    @Schema(description = "外部订单id")
    private List<String> outOrderIds;

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
    @Schema(description = "操作日志记录")
    private List<TradeEventLogVO> tradeEventLogs = new ArrayList<>();

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
     * 邀请人分销员id
     */
    @Schema(description = "邀请人分销员id")
    private String distributorId;

    /**
     * 邀请人会员id
     */
    @Schema(description = "邀请人会员id")
    private String inviteeId;

    /**
     * 基础分销设置-小店名称
     */
    @Schema(description = "小店名称")
    private String shopName;

    /**
     * 佣金（订单返利）
     */
    @Schema(description = "佣金（订单返利）")
    private BigDecimal commission;

    /**
     * 是否返利
     */
    @Schema(description = "是否返利",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean commissionFlag;

    /**
     * 分销单品列表
     */
    private List<TradeDistributeItemVO> distributeItems = new ArrayList<>();
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
     * 是否三次拆单
     */
    @Schema(description = "是否三次拆单")
    private Boolean splitOrderFlag = false;

    /**
     * @return
     */
    @JsonIgnore
    public ConcurrentHashMap<String, TradeItemVO> skuItemMap() {
        return new ConcurrentHashMap<>(
                tradeItems.stream().collect(Collectors.toMap(TradeItemVO::getSkuId, Function.identity())));
    }

    /**
     * @return
     */
    @JsonIgnore
    public ConcurrentHashMap<String, TradeItemVO> giftSkuItemMap() {
        return new ConcurrentHashMap<>(
                gifts.stream().collect(Collectors.toMap(TradeItemVO::getSkuId, Function.identity())));
    }

    /**
     * 是否被结算
     */
    @Schema(description = "是否被结算", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean hasBeanSettled;

    /**
     * 是否可退标识
     */
    @Schema(description = "是否可退标识", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean canReturnFlag;

    /**
     * 退款标识
     * 仅供结算使用 - 标识该订单是未收货的退款单子
     * <p>
     * 该单子flowState是作废不会入账，但是退单是COMPLETE状态会入账，导致收支不公，加了单独的状态作为判断
     */
    @Schema(description = "退款标识")
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
    @Schema(description = "运费优惠券信息")
    private TradeCouponVO freightCoupon;

    /**
     * 营销赠品全量列表
     */
    @Schema(description = "营销赠品全量列表")
    private List<TradeItemVO> gifts = new ArrayList<>();

    /**
     * 加价购商品列表
     */
    private List<TradeItemVO> preferential = new ArrayList<>();

    /**
     * 订单来源--区分h5,pc,app,小程序,代客下单
     */
    @Schema(description = "订单来源")
    private OrderSource orderSource;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private PayWay payWay;

    /**
     * 支付方式描述
     */
    @Schema(description = "支付方式描述")
    private String payWayValue;

    /**
     * 开店礼包
     */
    @Schema(description = "开店礼包")
    private DefaultFlag storeBagsFlag = DefaultFlag.NO;


    /**
     * 是否组合套装
     */
    @Schema(description = "是否组合套装")
    private Boolean suitMarketingFlag;

    /**
     * 分销员名称
     */
    @Schema(description = "分销员名称")
    private String distributorName;

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
     * 是否拼团订单
     */
    private Boolean grouponFlag;

    /**
     * 订单拼团信息
     */
    private TradeGrouponVO tradeGroupon;

    /**
     * 分销佣金提成信息列表
     */
    private List<TradeCommissionVO> commissions;

    /**
     * 总佣金(返利人佣金 + 提成人佣金)
     */
    private BigDecimal totalCommission;

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
     * 第三方内部卖家名称
     */
    private String thirdSellerName;

    /**
     * 第三方内部卖家编号
     */
    private String thirdSellerId;

    /**
     * 第三方平台支付失败状态  true:失败 false:成功
     */
    private Boolean thirdPlatformPayErrorFlag;

    /**
     * 子单是否全都包含商家订单
     */
    private Boolean isContainsTrade;

    /**
     * 是否是预售商品
     */
    private Boolean isBookingSaleGoods;

    /**
     * 预售类型
     */
    private BookingType bookingType;

    /**
     * 尾款通知手机号
     */
    private String tailNoticeMobile;

    /**
     * 尾款支付单ID
     */
    private String tailPayOrderId;

    /**
     * 尾款订单号
     */
    private String tailOrderNo;

    /**
     * 未使用的优惠券
     */
    @Schema(description = "未使用的优惠券")
    private List<CouponCodeVO> couponCodes;

    @Schema(description = "可用优惠券数量（满减、满折）")
    private Integer couponAvailableCount;

    @Schema(description = "可用礼品卡数量")
    public Long giftCardNum;

    /**
     * 积分订单的子订单列表，拷贝PointsTradeVO时使用
     */
    @Schema(description = "积分订单的子订单列表，拷贝PointsTradeVO时使用")
    private List<PointsTradeVO> pointsTradeVOList;

    /**
     * 积分订单优惠券，拷贝PointsTradeVO时使用
     */
    @Schema(description = "积分订单优惠券，拷贝PointsTradeVO时使用")
    private TradePointsCouponItemVO tradeCouponItem;

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
     * 授信支付信息
     */
    @Schema(description = "授信支付信息")
    private CreditPayInfoVO creditPayInfo;

    /**
     * 需要授信还款
     */
    @Schema(description = "需要授信还款")
    private Boolean needCreditRepayFlag;

    /**
     * 可还款金额
     * 场景: 授信还款关联订单列表
     */
    @Schema(description = "可还款金额")
    private BigDecimal canRepayPrice;

    /**
     * 授信订单存在正在进行中的退单
     */
    @Schema(description = "是否可退标识")
    private Boolean returningFlag;

    /**
     * 授信订单是否可选
     */
    @Schema(description = "是否可选 true 可以 false 不可选中")
    private Boolean canCheckFlag;

    /**
     * 是否可一键发货标识
     */
    @Schema(description = "是否可一键发货标识", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean canDeliveryFlag;

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
     * 当前订单是否在售后
     */
    @Schema(description = "当前订单是否正在售后中")
    private Boolean isReturn = false;

    /**
     * 当前订单商品是否全部申请售后或售后完成
     */
    @Schema(description = "当前订单商品是否全部申请售后或售后完成")
    private Boolean isAllReturn = false;

    /**
     * 运费信息
     */
    @Schema(description = "运费信息")
    private FreightVO freight;

    /**
     * 是否有售后
     */
    @Schema(description = "订单是否有售后")
    private Boolean isHasPostSales = Boolean.FALSE;

    /**
     * 是否允许在途退货 TRUE 允许 FALSE 不允许
     */
    private Boolean transitReturn = Boolean.FALSE;

    /**
     * 有售后中所有退单id的集合
     */
    @Schema(description = "有售后中所有退单id的集合")
    private List<String> returnOrderIdList;

    /**
     * 主订单导出合并单元格列数
     */
    private int rowNum;

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

    /***
     * 该值在订单为同城配送
     * 骑手取货回调中填充
     * 如果骑手取消->商家确认取消，则删除该字段
     */
    @Schema(description = "收货码")
    private String orderFinishCode;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

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
     * 带货视频号
     */
    @Schema(description = "带货视频号")
    private VideoUserVO videoUser;

    /**
     * 场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）
     */
    @Schema(description = "场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）")
    private Integer sceneGroup;

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
     * 付费会员信息
     */
    @Schema(description = "付费会员信息")
    private PayingMemberInfoVO payingMemberInfo;


    /**
     * 正在进行的退单数量
     */
    private Integer returnOrderNum = 0;
    /**
     * payOrder付款单信息
     **/
    @Schema(description = "payOrder付款单信息")
    private List<PayOrderResponseVO> payOrderResponses;

    /**
     * 尾款开始支付
     */
    @Schema(description = "尾款开始支付")
    private Boolean bookingStartSendFlag = Boolean.FALSE;

    /**
     * 尾款支付超时提醒
     */
    @Schema(description = "尾款支付超时提醒")
    private Boolean bookingEndSendFlag = Boolean.FALSE;

    /**
     * 是否是砍价订单
     */
    private Boolean bargain;

    /**
     * 砍价记录订单
     */
    private Long bargainId;

    /**
     * 支付版本号
     * 默认10，每次拉起支付后加1
     */
    private int payVersion;


    /**
     * 周期购信息
     */
    @Schema(description = "周期购信息")
    private TradeBuyCycleVO tradeBuyCycle;

    /**
     * 标识买家是否已修改过收货地址
     */
    @Schema(description = "标识买家是否已修改过收货地址")
    private Boolean buyerHasModifiedConsignee = Boolean.FALSE;

    /**
     * 最新一次更新收货地址的操作方（目前的用途，仅用于判断供应商端是否展示"信息变更"）
     */
    private Platform newestConsigneeUpdaterPlatform;

    /**
     * 是否有申请中或已完成的退单
     */
    @Schema(description = "是否有申请中或已完成的退单")
    private Boolean hasReturn;

    /**
     * 社区团购
     */
    @Schema(description = "社区团购对象")
    private CommunityTradeCommissionVO communityTradeCommission;

    /**
     * 拉卡拉订单交易信息
     */
    @Schema(description = "拉卡拉订单交易信息")
    private LklOrderTradeInfoVO lklOrderTradeInfo;

    /**
     * 微信物流信息token
     */
    @Schema(name = "微信物流信息token")
    private String waybillToken;

    /**
     * 微信物流信息状态:  0: 运单不存在或者未揽收、1: 已揽件、2: 运输中、3: 派件中、4: 已签收、5: 异常、6: 代签收
     */
    @Schema(name = "微信物流信息状态")
    private String waybillStatus;
}
