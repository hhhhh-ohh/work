package com.wanmi.sbc.goods.goodsaudit.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 黄昭
 * @className GoodsAuditEditResponse
 * @description TODO
 * @date 2021/12/21 17:38
 **/
@Data
@Schema
public class GoodsAuditEditResponse extends BasicResponse {

    /**
     * 商品信息
     */
    private GoodsAuditSaveVO goodsAudit;

    /**
     * 商品相关图片
     */
    private List<GoodsImageVO> images;

    /**
     * 商品属性列表
     */
    private List<GoodsPropertyDetailRelVO> goodsPropDetailRels;

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

    /**
     * 是否修改
     */
    private Boolean changeFlag;

    /**
     * 新老sku关联关系 key:goodsInfoId value:oldGoodsInfoId
     */
    private Map<String,String> goodsInfoMap;
}