package com.wanmi.sbc.goods.api.response.price;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.intervalprice.GoodsIntervalPriceResponse
 * 商品区间价格响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/6 下午2:32
 */
@Schema
@Data
public class GoodsIntervalPriceResponse extends BasicResponse {

    private static final long serialVersionUID = -3434000876821123863L;

    /**
     * 区间价列表
     */
    @Schema(description = "区间价列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPriceVOList = new ArrayList<>();

    /**
     * 商品列表
     */
    @Schema(description = "商品列表")
    private List<GoodsInfoVO> goodsInfoVOList;
}
