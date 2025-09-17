package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.order.bean.vo.PurchaseGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Schema
public class PurchaseMiniListResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 商品
     */
    @Schema(description = "商品列表")
    private List<PurchaseGoodsVO> goodsList;

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 采购单数量
     */
    @Schema(description = "采购单数量")
    private Integer purchaseCount;

    /**
     * 购买商品总计
     */
    @Schema(description = "购买商品总计")
    private Long num;

}
