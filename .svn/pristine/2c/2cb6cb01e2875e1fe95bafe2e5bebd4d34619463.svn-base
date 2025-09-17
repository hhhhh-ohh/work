package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>订单确认返回项</p>
 * Created by of628-wenzhi on 2018-03-08-下午6:12.
 */
@Data
@Schema
public class TradeConfirmItemVO extends BasicResponse {
    /**
     * 订单商品sku
     */
    @Schema(description = "订单商品sku")
    private List<TradeItemVO> tradeItems;

    /**
     * 赠品列表
     */
    @Schema(description = "赠品列表")
    private List<TradeItemVO> gifts;

    @Schema(description = "加价购商品列表")
    private List<TradeItemVO> preferential;

    /**
     * 订单营销信息
     */
    @Schema(description = "营销信息")
    private List<TradeConfirmMarketingVO> tradeConfirmMarketingList;

    /**
     * 商家与店铺信息
     */
    @Schema(description = "商家与店铺信息")
    private SupplierVO supplier;

    /**
     * 订单项小计
     */
    @Schema(description = "订单项小计")
    private TradePriceVO tradePrice;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private List<DiscountsVO> discountsPrice;


    /**
     * 跨境订单的扩展字段
     */
    @Schema(description = "跨境订单的扩展字段")
    private Object extendedAttributes;


    /**
     * 周期购购买期数
     */
    @Schema(description = "周期购购买期数")
    private Integer buyCycleNum;

    /**
     * 周期购信息
     */
    @Schema(description = "周期购信息")
    private TradeBuyCycleVO tradeBuyCycleVO;
}
