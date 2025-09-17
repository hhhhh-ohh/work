package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.ShipperType;
import com.wanmi.sbc.order.bean.vo.ShippingItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema
public class TradeDeliverDTO extends BaseQueryRequest {

    /**
     * 发货单属于的订单号
     */
    @Schema(description = "发货单属于的订单号")
    private String tradeId;

    /**
     * 订单的所属商家/供应商 名称
     */
    @Schema(description = "订单的所属商家/供应商")
    private String providerName;

    /**
     * 发货单号
     */
    @Schema(description = "发货单号")
    private String deliverId;

    /**
     * 物流信息
     */
    @Schema(description = "物流信息")
    private LogisticsDTO logistics;

    /**
     * 收货人信息
     */
    @Schema(description = "收货人信息")
    private ConsigneeDTO consignee;

    /**
     * 发货清单
     */
    @Schema(description = "发货清单")
    private List<ShippingItemDTO> shippingItems = new ArrayList<>();

    /**
     * 赠品信息
     */
    @Schema(description = "赠品信息")
    private List<ShippingItemDTO> giftItemList = new ArrayList<>();

    @Schema(description = "加价购商品信息")
    private List<ShippingItemVO> preferentialItemList = new ArrayList<>();

    /**
     * 发货时间
     */
    @Schema(description = "发货时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deliverTime;

    /**
     * 发货状态
     */
    @Schema(description = "发货状态")
    private DeliverStatus status;

    /**
     * 子订单 发货单号
     */
    @Schema(description = "子订单 发货单号")
    private String sunDeliverId;

    /**
     * 所属 商家/供应商
     */
    @Schema(description = "所属 商家/供应商")
    private ShipperType shipperType;

    /**
     * 第三方平台订单物流标识
     */
    @Schema(description = "第三方平台订单物流标识")
    private ThirdPlatformType thirdPlatformType;

    /**
     * 周期购配送计划
     */
    @Schema(description = "周期购配送计划")
    private CycleDeliveryPlanDTO cycleDeliveryPlan;
}
