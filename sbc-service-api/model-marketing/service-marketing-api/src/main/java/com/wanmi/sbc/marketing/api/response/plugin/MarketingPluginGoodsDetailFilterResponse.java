package com.wanmi.sbc.marketing.api.response.plugin;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-14
 */
@Schema
@Data
public class MarketingPluginGoodsDetailFilterResponse extends BasicResponse {

    private static final long serialVersionUID = -757547647657530917L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private GoodsInfoVO goodsInfo;

    /**
     * 相关商品SPU信息
     */
    @Schema(description = "相关商品SPU信息")
    private GoodsVO goods;

    /**
     * 商品属性列表
     */
    @Schema(description = "商品属性列表")
    private List<GoodsPropDetailRelVO> goodsPropDetailRels;

    /**
     * 商品规格列表
     */
    @Schema(description = "商品规格列表")
    private List<GoodsSpecVO> goodsSpecs = new ArrayList<>();

    /**
     * 商品规格值列表
     */
    @Schema(description = "商品规格值列表")
    private List<GoodsSpecDetailVO> goodsSpecDetails = new ArrayList<>();

    /**
     * 商品等级价格列表
     */
    @Schema(description = "商品等级价格列表")
    private List<GoodsLevelPriceVO> goodsLevelPrices = new ArrayList<>();

    /**
     * 商品客户价格列表
     */
    @Schema(description = "商品客户价格列表")
    private List<GoodsCustomerPriceVO> goodsCustomerPrices = new ArrayList<>();

    /**
     * 商品订货区间价格列表
     */
    @Schema(description = "商品订货区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices = new ArrayList<>();

    /**
     * 商品相关图片
     */
    @Schema(description = "商品相关图片")
    private List<GoodsImageVO> images = new ArrayList<>();

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 商家类型
     */
    @Schema(description = "是否是商家")
    private BoolFlag companyType;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;
}
