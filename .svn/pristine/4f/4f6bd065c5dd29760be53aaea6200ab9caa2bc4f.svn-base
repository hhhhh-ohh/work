package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车处理社交分销业务
 */
@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase4DistributionResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private List<GoodsInfoVO> goodsInfos;

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 商品SKU信息-排除分销商品
     */
    @Schema(description = "商品SKU信息-排除分销商品")
    private List<GoodsInfoVO> goodsInfoComList;

    /**
     * 是否自购-显示返利
     */
    @Schema(description = "是否自购")
    private boolean selfBuying = false;
}
