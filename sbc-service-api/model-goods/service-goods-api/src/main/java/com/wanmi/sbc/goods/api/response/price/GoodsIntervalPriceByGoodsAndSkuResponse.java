package com.wanmi.sbc.goods.api.response.price;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByGoodsAndSkuResponse
 *
 * @author lipeng
 * @dateTime 2018/11/13 上午9:49
 */
@Schema
@Data
public class GoodsIntervalPriceByGoodsAndSkuResponse extends BasicResponse {

    private static final long serialVersionUID = 4503391529119105049L;

    /**
     * 区间价列表
     */
    @Schema(description = "区间价列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPriceVOList = new ArrayList<>();

    /**
     * 商品Sku列表
     */
    @Schema(description = "商品Sku列表")
    private List<GoodsInfoVO> goodsInfoVOList;

    /**
     * 商品列表
     */
    @Schema(description = "商品列表")
    private List<GoodsVO> goodsDTOList;
}
