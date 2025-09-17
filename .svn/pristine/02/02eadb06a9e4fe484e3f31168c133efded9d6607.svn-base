package com.wanmi.sbc.order.trade.model.root;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.VideoUserVO;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import com.wanmi.sbc.marketing.bean.vo.TradeCouponVO;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO;
import com.wanmi.sbc.order.bean.dto.TradeBuyCycleDTO;
import com.wanmi.sbc.order.bean.enums.EvaluateStatus;
import com.wanmi.sbc.order.bean.enums.OrderSource;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;
import com.wanmi.sbc.order.bean.vo.LklOrderTradeInfoVO;
import com.wanmi.sbc.order.trade.model.entity.*;
import com.wanmi.sbc.order.trade.model.entity.value.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
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
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Id
    private String id;

    /**
     * 父订单号，用于组织批量订单合并支付，目前仅在支付与退款中使用。
     */
    private String parentId;

    /**
     * 订单组号
     */
    private String groupId;

    /**
     * 购买人
     */
    private Buyer buyer;

    /**
     * boss卖方
     */
    private Seller seller;

    /**
     * 商家
     */
    private Supplier supplier;

    /**
     * 买家备注
     */
    private String buyerRemark;

    /**
     * 卖家备注
     */
    private String sellerRemark;

    /**
     * 订单附件，以逗号隔开
     */
    private String encloses;

    /**
     * 调用方的请求 IP
     * added by shenchunnan
     */
    private String requestIp;

    /**
     * 发票
     */
    private Invoice invoice;

    /**
     * 订单总体状态
     */
    private TradeState tradeState;

    /**
     * 收货人信息
     */
    private Consignee consignee;

    /**
     * 订单价格
     */
    private TradePrice tradePrice;

    /**
     * 订单商品列表
     */
    private List<TradeItem> tradeItems = new ArrayList<>();

    /**
     * 积分订单优惠券
     */
    private TradePointsCouponItem tradeCouponItem;

    /**
     * 发货单
     */
    private List<TradeDeliver> tradeDelivers = new ArrayList<>();

    /**
     * 配送方式
     */
    private DeliverWay deliverWay;


    private PayInfo payInfo;

    /**
     * 支付单ID
     */
    private String payOrderId;

    /**
     * 尾款支付单ID
     */
    private String tailPayOrderId;


    /**
     * 订单来源方
     */
    private Platform platform;

    /**
     * 订单所属第三方平台类型
     * Trade表弃用，采用thirdPlatformTypes判断包含是否该渠道订单  如linkedMall 京东
     * providerTrade和ThirdPlatformTrade继续以此字段来判断属于渠道
     *  **** 枚举：WECHAT_VIDEO 不可为订单此属性赋值 ****
     */
    private ThirdPlatformType thirdPlatformType;

    /**
     * 订单包含第三方平台类型
     */
    private List<ThirdPlatformType> thirdPlatformTypes;

    /**
     * 第三方供应商运费
     */
    private BigDecimal thirdPlatFormFreight;

    /**
     * 第三方平台支付失败状态  true:失败 false:成功
     */
    private Boolean thirdPlatformPayErrorFlag;

    /**
     * 下单时是否已开启订单自动审核
     */
    private Boolean isAuditOpen = Boolean.TRUE;

    /**
     * 订单支付顺序
     */
    private PaymentOrder paymentOrder;

    /**
     * 超时未支付取消订单时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderTimeOut;

    /**
     * 操作日志记录（状态变更）
     */
    private List<TradeEventLog> tradeEventLogs = new ArrayList<>();

    /**
     * 分销渠道类型
     */
    private ChannelType channelType;

    /**
     * 小B店铺内分享链接携带的邀请人ID（会员ID）
     */
    private String distributionShareCustomerId;

    /**
     * 返利人分销员id
     */
    private String distributorId;

    /**
     * 返利人会员id
     */
    private String inviteeId;

    /**
     * 小店名称
     */
    private String shopName;

    /**
     * 返利人名称
     */
    private String distributorName;

    /**
     * 开店礼包
     */
    private DefaultFlag storeBagsFlag = DefaultFlag.NO;

    /**
     * 是否组合套装
     */
    private Boolean suitMarketingFlag;

    /**
     * 开店礼包邀请人id
     */
    private String storeBagsInviteeId;

    /**
     * 分销单品列表
     */
    private List<TradeDistributeItem> distributeItems = new ArrayList<>();

    /**
     * 返利人佣金
     */
    private BigDecimal commission = BigDecimal.ZERO;

    /**
     * 总佣金(返利人佣金 + 提成人佣金)
     */
    private BigDecimal totalCommission = BigDecimal.ZERO;

    /**
     * 提成人佣金列表
     */
    private List<TradeCommission> commissions = new ArrayList<>();

    /**
     * 是否返利
     */
    private Boolean commissionFlag = Boolean.FALSE;

    /**
     * 正在进行的退单数量
     */
    private Integer returnOrderNum = 0;

    /**
     * 是否被结算
     */
    private Boolean hasBeanSettled;

    /**
     * 是否可退标识
     */
    private Boolean canReturnFlag;

    /**
     * 退款标识
     * 仅供结算使用 - 标识该订单是未收货的退款单子
     * <p>
     * 该单子flowState是作废不会入账，但是退单是COMPLETE状态会入账，导致收支不公，加了单独的状态作为判断
     */
    private Boolean refundFlag;

    /**
     * 订单营销信息
     */
    private List<TradeMarketingVO> tradeMarketings;


    /**
     * 订单使用的店铺优惠券
     */
    private TradeCouponVO tradeCoupon;

    /**
     * 运费优惠券信息
     */
    private TradeCouponVO freightCoupon;

    /**
     * 营销赠品全量列表
     */
    private List<TradeItem> gifts = new ArrayList<>();

    /**
     * 加价购商品列表
     */
    private List<TradeItem> preferential = new ArrayList<>();

    /**
     * 订单来源--区分h5,pc,app,小程序,代客下单
     */
    private OrderSource orderSource;

    /**
     * 订单评价状态
     */
    private EvaluateStatus orderEvaluateStatus = EvaluateStatus.NO_EVALUATE;

    /**
     * 店铺服务评价状态
     */
    private EvaluateStatus storeEvaluate = EvaluateStatus.NO_EVALUATE;

    /**
     * 支付方式
     */
    private PayWay payWay;

    /**
     * 可退积分
     */
    private Long canReturnPoints;

    /**
     * 已退金额
     */
    private BigDecimal canReturnPrice;

    /**
     * 订单类型 0：普通订单；1：积分订单；
     */
    private OrderType orderType;

    /**
     * 积分订单类型 0：积分商品 1：积分优惠券
     */
    private PointsOrderType pointsOrderType;

    /**
     * 分享人id
     */
    private String shareUserId;

    /**
     * 是否是秒杀抢购商品订单
     */
    private Boolean isFlashSaleGoods;

    /**
     * 是否是限时抢购商品订单
     */
    private Boolean isFlashPromotionGoods;

    /**
     * 是否是预售商品
     */
    private Boolean isBookingSaleGoods;

    /**
     * 预售类型
     */
    private BookingType bookingType;

    /**
     * 尾款订单号
     */
    private String tailOrderNo;

    /**
     * 尾款通知手机号
     */
    private String tailNoticeMobile;

    /**
     * 是否拼团订单
     */
    private Boolean grouponFlag = Boolean.FALSE;

    /**
     * 订单拼团信息
     */
    private TradeGroupon tradeGroupon;

    /**
     * 是否授信支付
     */
    private Boolean  creditPayFlag;

    /**
     * 可还款金额
     * 场景: 授信还款关联订单列表
     */
    private BigDecimal canRepayPrice;

    /**
     * 授信支付信息
     */
    private CreditPayInfo creditPayInfo;

    /**
     * 需要授信还款
     */
    private Boolean needCreditRepayFlag;

    /**
     * 授信订单存在正在进行中的退单
     */
    private Boolean returningFlag;

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
     * 是否自提订单
     */
    @Schema(description = "是否自提订单")
    private Boolean pickupFlag;

    /**
     * 自提信息
     */
    @Schema(description = "自提信息")
    private PickSettingInfo pickSettingInfo;

    /**
     * 核销信息
     */
    @Schema(description = "核销信息")
    private WriteOffInfo writeOffInfo;

    /**
     * 运费信息
     */
    @Schema(description = "运费信息")
    private Freight freight;

    /**
     * 是否允许在途退货 TRUE 允许 FALSE 不允许
     */
    @Schema(description = "是否允许在途退货 TRUE 允许 FALSE 不允许")
    private Boolean transitReturn = Boolean.FALSE;

    /**
     * 订单标记
     */
    @Schema(description = "订单标记")
    private OrderTag orderTag;

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
     * 满返发券信息
     */
    @Schema(description = "满返发券信息")
    private List<FullReturnCoupon> fullReturnCoupons = new ArrayList<>();

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
    private PayingMemberInfo payingMemberInfo;

    /**
     * 自动确认收货前24小时,是否发送订阅消息
     */
    @Schema(description = "自动确认收货前24小时,是否发送订阅消息")
    private Boolean orderAutoReceiveSendMiniProgramMsgFlag = Boolean.FALSE;

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
     * 组合购活动id
     */
    private Long suitsId;


    /**
     * 周期购信息
     */
    private TradeBuyCycleDTO tradeBuyCycle;

    /**
     * 标识买家是否已修改过收货地址
     */
    private Boolean buyerHasModifiedConsignee = Boolean.FALSE;

    /**
     * 售后(有无)
     * @see com.wanmi.sbc.order.trade.service.TradeService#updateReturnOrderNum
     */
    @Schema(description = "售后(有无)")
    private Boolean hasReturn ;

    /**
     * 社区团购
     */
    private CommunityTradeCommission communityTradeCommission;

    /**
     * 拉卡拉订单交易信息
     */
    @Schema(description = "拉卡拉订单交易信息")
    private LklOrderTradeInfoVO lklOrderTradeInfo;

    /**
     * 是否需要预约发货: 默认 0-不需要; 1-需要
     */
    private Integer appointmentShipmentFlag;

    /**
     * 预约发货时间
     */
    private String appointmentShipmentTime;

    /**
     * 增加
     *
     * @param log
     * @return
     */
    public List<TradeEventLog> appendTradeEventLog(TradeEventLog log) {
        tradeEventLogs.add(0, log);
        return tradeEventLogs;
    }

    /**
     * 增加发货单
     *
     * @param tradeDeliver 收款单信息
     */
    public void addTradeDeliver(TradeDeliver tradeDeliver) {
        tradeDelivers.add(0, tradeDeliver);
    }


    /**
     * @return
     */
    @JsonIgnore
    public ConcurrentHashMap<String, TradeItem> skuItemMap() {
        return new ConcurrentHashMap<>(
                tradeItems.stream().collect(Collectors.toMap(TradeItem::getSkuId, Function.identity())));
    }

    /**
     * @return
     */
    @JsonIgnore
    public ConcurrentHashMap<Long, List<TradeItem>> giftSkuItemMap() {
        return new ConcurrentHashMap<>(
                gifts.stream().collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0))));
    }

    @JsonIgnore
    public ConcurrentHashMap<Long, List<TradeItem>> preferentialItemMap() {
        return new ConcurrentHashMap<>(
                preferential.stream().collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0))));
    }

}
