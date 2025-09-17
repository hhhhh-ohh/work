package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.vo.TradeGrouponVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单状态
 * Created by jinwei on 14/3/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class GrouponTradeVO extends BasicResponse {

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
     * 商家
     */
    @Schema(description = "商家")
    private SupplierVO supplier;

    /**
     * boss卖方
     */
    @Schema(description = "boss卖方")
    private SellerVO seller;

    /**
     * 订单实际支付金额
     * 账务中心每次回调的支付金额之和：订单已支付金额
     * add wumeng
     */
    @Schema(description = "订单实际支付金额")
    private BigDecimal totalPayCash;

    /**
     * 超时未支付取消订单时间
     */
    @Schema(description = "超时未支付取消订单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderTimeOut;

    /**
     * 订单价格
     */
    @Schema(description = "订单价格")
    private TradePriceVO tradePrice;

    /**
     * 团实例信息
     */
    private GrouponInstanceVO grouponInstance;

    /**
     * 订单商品列表
     */
    @Schema(description = "订单商品列表")
    private List<TradeItemVO> tradeItems = new ArrayList<>();

    /**
     * 订单拼团信息
     */
    private TradeGrouponVO tradeGroupon;

    /**
     * 订单总体状态
     */
    @Schema(description = "订单总体状态")
    private TradeStateVO tradeState;
}
