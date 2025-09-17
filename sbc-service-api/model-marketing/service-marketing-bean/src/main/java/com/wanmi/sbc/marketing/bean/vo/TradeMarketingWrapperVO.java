package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午10:39 2018/10/8
 * @Description:订单营销插件响应类
 */
@Schema
@Data
public class TradeMarketingWrapperVO extends BasicResponse {

    private static final long serialVersionUID = -2073948578118830172L;

    /**
     * 满系营销实体 {@link TradeMarketingVO}
     */
    @Schema(description = "订单营销信息")
    private TradeMarketingVO tradeMarketing;

    /**
     * 优惠券实体 {@link TradeCouponVO}
     */
    @Schema(description = "订单优惠券信息")
    private TradeCouponVO tradeCoupon;

}
