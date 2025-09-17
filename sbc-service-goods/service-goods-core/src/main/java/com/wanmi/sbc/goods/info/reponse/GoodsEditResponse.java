package com.wanmi.sbc.goods.info.reponse;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.Data;

import java.util.List;

/**
 * 商品编辑视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class GoodsEditResponse extends BasicResponse {

    /**
     * 商品信息
     */
    private GoodsSaveVO goods;

    /**
     * 商品相关图片
     */
    private List<GoodsImageVO> images;

    /**
     * 商品主图图片
     */
    private List<GoodsMainImageVO> mainImage;

    /**
     * 商品属性列表
     */
    private List<GoodsPropDetailRelVO> goodsPropDetailRels;

    /**
     * 商品规格列表
     */
    private List<GoodsSpecSaveVO> goodsSpecs;

    /**
     * 商品规格值列表
     */
    private List<GoodsSpecDetailSaveVO> goodsSpecDetails;

    /**
     * 商品SKU列表
     */
    private List<GoodsInfoSaveVO> goodsInfos;

    /**
     * 积分商品列表
     */
    private List<PointsGoodsSaveVO> pointsGoodsList;

    /**
     * 商品等级价格列表
     */
    private List<GoodsLevelPriceVO> goodsLevelPrices;

    /**
     * 商品客户价格列表
     */
    private List<GoodsCustomerPriceVO> goodsCustomerPrices;

    /**
     * 商品订货区间价格列表
     */
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 商品详情模板关联
     */
    private List<GoodsTabRelaVO> goodsTabRelas;

    /**
     * 商品模板配置
     */
    private List<StoreGoodsTabSaveVO> storeGoodsTabs;

    /**
     * 商品sku等级价格列表
     */
    private List<GoodsLevelPriceVO> goodsInfoLevelPrices;

    /**
     * 商品sku客户价格列表
     */
    private List<GoodsCustomerPriceVO> goodsInfoCustomerPrices;

    /**
     * 商品sku订货区间价格列表
     */
    private List<GoodsIntervalPriceVO> goodsInfoIntervalPrices;

}
