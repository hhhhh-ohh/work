package com.wanmi.sbc.goods.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateSumVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Auther: jiaojiao
 * @Date: 2019/3/12 10:11
 * @Description:跳转到评价页面的 商品信息和商家信息
 */
@Schema
@Data
public class GoodsEvaluateQueryGoodsAndStoreResponse extends BasicResponse {

    /**
     * 订单信息
     */
    @Schema(description = "订单信息")
    private TradeItemVO tradeVO;

    /**
     * 店铺评分聚合信息
     */
    @Schema(description = "店铺评分聚合信息")
    private StoreEvaluateSumVO storeEvaluateSumVO;

    /**
     * 订单商品是否已评价
     * 0：未评价 1：已评价
     */
    @Schema(description = "订单商品是否已评价")
    private Integer goodsTobe = 0;

    /**
     * 店铺服务是否已评价
     * 0：未评价 1：已评价
     */
    @Schema(description = "店铺服务是否已评价")
    private Integer storeTobe = 0;

    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private String tid;

    /**
     * 店铺信息
     */
    @Schema(description = "店铺信息")
    private StoreVO storeVO;

    /**
     * 订单时间
     */
    @Schema(description = "订单时间")
    private String createTime;

}
