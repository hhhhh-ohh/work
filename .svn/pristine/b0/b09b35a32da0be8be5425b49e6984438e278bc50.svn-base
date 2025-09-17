package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.PointsOrderType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import com.wanmi.sbc.order.bean.enums.OrderSource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName PointsTradeVo
 * @Description 积分订单Vo
 * @Author lvzhenwei
 * @Date 2019/5/10 13:58
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PointsTradeVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String id;

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
     * 积分订单优惠券
     */
    @Schema(description = "积分订单优惠券")
    private TradePointsCouponItemVO tradeCouponItem;

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
     * 订单所属第三方平台的订单id
     */
    @Schema(description = "订单所属第三方平台的订单id")
    private List<String> thirdPlatformOrderIds;

    /**
     * 外部订单id
     * linkedMall -> 淘宝订单号
     */
    @Schema(description = "外部订单id")
    private List<String> outOrderIds;

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
     * @return
     */
    @JsonIgnore
    public ConcurrentHashMap<String, TradeItemVO> skuItemMap() {
        return new ConcurrentHashMap<>(
                tradeItems.stream().collect(Collectors.toMap(TradeItemVO::getSkuId, Function.identity())));
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
     * 订单类型 0：普通订单；1：积分订单；
     */
    @Schema(description = "订单类型 0：普通订单；1：积分订单")
    private OrderType orderType;

    /**
     * 积分订单类型 0：积分商品 1：积分优惠券
     */
    private PointsOrderType pointsOrderType;

    /**
     * 主订单id(商户)
     */
    private String parentId;


    /**
     * 子订单列表
     */
    @Schema(description = "子订单列表")
    private List<PointsTradeVO> pointsTradeVOList;

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
    @Schema(description = "子单是否全都包含商家订单")
    private Boolean isContainsTrade;


    /**
     * 卡密信息
     */
    @Schema(description = "卡密信息")
    private List<ElectronicCardVO> electronicCards;


    /**
     * 核销信息
     */
    @Schema(description = "核销信息")
    private WriteOffInfoVO writeOffInfo;


    /**
     * 订单标记
     */
    @Schema(description = "订单标记")
    private OrderTagVO orderTag;
    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

}
