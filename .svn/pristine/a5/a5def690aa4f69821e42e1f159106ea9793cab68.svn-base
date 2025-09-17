package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>订单成功提交的返回信息</p>
 * Created by of628-wenzhi on 2017-07-25-下午3:52.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeCommitResultVO extends BasicResponse {

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String tid;

    /**
     * 父订单号，用于不同商家订单合并支付场景
     */
    @Schema(description = "父订单号")
    private String parentTid;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private TradeStateVO tradeState;

    /**
     * 订单支付顺序
     */
    @Schema(description = "订单支付顺序")
    private PaymentOrder paymentOrder;

    /**
     * 交易金额
     */
    @Schema(description = "交易金额")
    private BigDecimal price;

    /**
     * 订单总金额
     */
    @Schema(description = "订单总金额")
    private BigDecimal totalPrice;

    /**
     * 订单取消时间
     */
    @Schema(description = "订单取消时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderTimeOut;

    /**
     * 是否是预售商品
     */
    @Schema(description = "是否是预售商品")
    private Boolean isBookingSaleGoods;

    /**
     * 预售类型
     */
    private BookingType bookingType;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 是否平台自营
     */
    @Schema(description = "是否平台自营",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean isSelf;
    /**
     * 是否跨境订单
     */
    @Schema(description = "是否跨境订单")
    private Boolean crossBorderFlag = false;

    @Schema(description = "店铺类型")
    private StoreType storeType;
}
