package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.bean.vo.TradeCouponVO;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO;
import com.wanmi.sbc.order.bean.enums.OrderSource;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;
import com.wanmi.sbc.order.bean.vo.FreightVO;
import com.wanmi.sbc.order.bean.vo.OrderTagVO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单状态
 * Created by jinwei on 14/3/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeAddDTO implements Serializable {

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
     * 订单来源方
     */
    @Schema(description = "订单来源方")
    private Platform platform;

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
     * 营销赠品全量列表
     */
    @Schema(description = "营销赠品全量列表")
    private List<TradeItemDTO> gifts = new ArrayList<>();

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
     * 跨境订单的扩展字段
     */
    @Schema(description = "跨境订单的扩展字段")
    private Object extendedAttributes;

    /**
     * 运费信息
     */
    @Schema(description = "运费信息")
    private FreightVO freight;

    /**
     * 是否允许在途退货 TRUE 允许 FALSE 不允许
     */
    private Boolean transitReturn = Boolean.FALSE;


    /**
     * 订单标记
     */
    @Schema(description = "订单标记")
    private OrderTagVO orderTag;

}
