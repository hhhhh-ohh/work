package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Created by sunkun on 2017/8/16.
 */
@Data
@Schema
public class MiniPurchaseResponse extends BasicResponse {

    /**
     * 商品
     */
    @Schema(description = "商品")
    private List<PurchaseGoodsReponse> goodsList;

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 采购单数量
     */
    @Schema(description = "采购单数量",example = "0")
    private Integer purchaseCount;

    /**
     * 购买商品总计
     */
    @Schema(description = "购买商品总计")
    private Long num;

}
