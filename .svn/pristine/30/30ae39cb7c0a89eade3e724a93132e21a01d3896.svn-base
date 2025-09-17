package com.wanmi.sbc.goods.api.response.price;

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
 * <p>企业购商品设价返回结构</p>
 * Created by of628-wenzhi on 2020-03-04-下午507.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsPriceSetBatchByIepResponse extends BasicResponse {
    private static final long serialVersionUID = 6439015921118014898L;

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
}
