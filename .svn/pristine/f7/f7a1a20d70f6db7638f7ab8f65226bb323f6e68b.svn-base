package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsSavePriceRequest
 * 新增商品定价请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午10:30
 */
@Schema
@Data
public class GoodsAddPriceRequest extends BaseRequest {

    private static final long serialVersionUID = 2318375395056590734L;

    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    private GoodsVO goods;

    /**
     * 商品相关图片
     */
    @Schema(description = "商品相关图片")
    private List<GoodsImageVO> images;

    /**
     * 商品属性列表
     */
    @Schema(description = "商品属性列表")
    private List<GoodsPropDetailRelVO> goodsPropDetailRels;

    /**
     * 商品规格列表
     */
    @Schema(description = "商品规格列表")
    private List<GoodsSpecVO> goodsSpecs;

    /**
     * 商品规格值列表
     */
    @Schema(description = "商品规格值列表")
    private List<GoodsSpecDetailVO> goodsSpecDetails;

    /**
     * 商品SKU列表
     */
    @Schema(description = "商品SKU列表")
    private List<GoodsInfoVO> goodsInfos;

    /**
     * 商品等级价格列表
     */
    @Schema(description = "商品等级价格列表")
    private List<GoodsLevelPriceVO> goodsLevelPrices;

    /**
     * 商品客户价格列表
     */
    @Schema(description = "商品客户价格列表")
    private List<GoodsCustomerPriceVO> goodsCustomerPrices;

    /**
     * 商品订货区间价格列表
     */
    @Schema(description = "商品订货区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 是否修改价格及订货量设置
     */
    @Schema(description = "是否修改价格及订货量设置", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isUpdatePrice;

    /**
     * 商品详情模板关联
     */
    @Schema(description = "商品详情模板关联")
    private List<GoodsTabRelaVO> goodsTabRelas;
}
