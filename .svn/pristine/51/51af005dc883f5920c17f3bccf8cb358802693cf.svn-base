package com.wanmi.sbc.vas.bean.vo.sellplatform;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author  wur
 * @date: 2022/5/7 10:56
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformTradeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String id;


    /**
     * 主订单号
     */
    @Schema(description = "主订单号")
    private String tradeId;

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
     * 调用方的请求 IP
     * added by shenchunnan
     */
    @Schema(description = "调用方的请求 IP")
    private String requestIp;


    /**
     * 收货人信息
     */
    @Schema(description = "收货人信息")
    private SellPlatformConsigneeVO consignee;

    /**
     * 订单价格
     */
    @Schema(description = "订单价格")
    private SellPlatformTradePriceVO tradePrice;

    /**
     * 订单商品列表
     */
    @Schema(description = "订单商品列表")
    private List<SellPlatformTradeItemVO> tradeItems = new ArrayList<>();

    /**
     * 购买人
     */
    @Schema(description = "购买人")
    private SellPlatformBuyerVO buyer;

    /**
     * 超时未支付取消订单时间
     */
    @Schema(description = "超时未支付取消订单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderTimeOut;

    /**
     * 营销赠品全量列表
     */
    @Schema(description = "营销赠品全量列表")
    private List<SellPlatformTradeItemVO> gifts = new ArrayList<>();

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

}
