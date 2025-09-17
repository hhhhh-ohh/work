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
 * com.wanmi.sbc.goods.api.response.intervalprice.GoodsIntervalPriceByCustomerIdResponse
 *
 * @author lipeng
 * @dateTime 2018/11/13 上午9:49
 */
@Schema
@Data
public class GoodsIntervalPriceByCustomerIdResponse extends BasicResponse {

    private static final long serialVersionUID = -5041929237032691766L;

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
