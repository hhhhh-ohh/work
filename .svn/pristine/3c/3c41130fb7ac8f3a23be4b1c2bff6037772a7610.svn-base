package com.wanmi.sbc.goods.info.reponse;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品SKU视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoResponse extends BasicResponse {

    /**
     * 分页商品SKU信息
     */
    private MicroServicePage<GoodsInfoSaveVO> goodsInfoPage;

    /**
     * 商品SKU信息
     */
    private List<GoodsInfoSaveVO> goodsInfos;

    /**
     * 供应商商品SKU信息
     */
    private List<GoodsInfoSaveVO> providerGoodsInfos;

    /**
     * 商品SPU信息
     */
    private List<GoodsSaveVO> goodses;

    /**
     * 商品区间价格列表
     */
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 品牌列表
     */
    private List<GoodsBrandVO> brands;

    /**
     * 分类列表
     */
    private List<GoodsCateVO> cates;
}
