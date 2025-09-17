package com.wanmi.sbc.goods.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateSumVO;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateImageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateInfoResponse extends BasicResponse {

    /**
     * 商品评论信息
     */
    @Schema(description = "商品评论信息")
    private GoodsEvaluateVO goodsEvaluateVO;

    /**
     * 商品评论信息-图片
     */
    @Schema(description = "商品评论信息-图片")
    private List<GoodsEvaluateImageVO> goodsEvaluateImageVOS;

    /**
     * 店铺评分信息
     */
    @Schema(description = "店铺评分信息")
    private StoreEvaluateVO storeEvaluateVO;

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
