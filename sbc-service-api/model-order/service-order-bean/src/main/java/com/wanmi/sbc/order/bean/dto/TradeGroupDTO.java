package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.marketing.bean.dto.TradeCouponDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午9:14 2018/9/30
 * @Description: 订单组
 */
@Data
@Schema
public class TradeGroupDTO {

    /**
     * 订单组号
     */
    @Schema(description = "订单组号")
    private String id;

    /**
     * 订单组中使用的平台优惠券
     */
    @Schema(description = "订单组中使用的平台优惠券")
    private TradeCouponDTO commonCoupon;

    /**
     * 使用平台优惠券的商品集合(已作废的商品)
     */
    @Schema(description = "使用平台优惠券的商品集合")
    private List<String> commonSkuIds = new ArrayList<>();

    /**
     * 平台券是否已退
     */
    @Schema(description = "平台券是否已退",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean commonCouponIsReturn = Boolean.FALSE;

}
