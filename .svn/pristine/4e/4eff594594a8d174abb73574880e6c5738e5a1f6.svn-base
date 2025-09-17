package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.setting.bean.vo.OperateDataLogVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse
 * 根据编号查询商品视图信息响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午9:39
 */
@Schema
@Data
public class GoodsViewByIdBaseResponse extends BasicResponse {

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
     * 商品主图图片
     */
    @Schema(description = "商品主图图片")
    private List<GoodsMainImageVO> mainImage;

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
     * 商品详情模板关联
     */
    @Schema(description = "商品详情模板关联")
    private List<GoodsTabRelaVO> goodsTabRelas;

    /**
     * 商品模板配置
     */
    @Schema(description = "商品模板配置")
    private List<StoreGoodsTabVO> storeGoodsTabs;

    /**
     * 是否是分销商品
     */
    @Schema(description = "是否是分销商品")
    private Boolean distributionGoods;

    /**
     * 拼团活动
     */
    @Schema(description = "拼团活动")
    private Boolean grouponFlag;

    /**
     * 操作日志
     */
    @Schema(description = "操作日志")
    private List<OperateDataLogVO> operateDataLogVOList;

    /**
     * 是否参与全量营销活动
     * 秒杀在购物车视为普通商品fullMarketing=false
     */
    @Schema(description = "是否参与全量营销活动")
    Boolean fullMarketing = Boolean.TRUE;

    /**
     * 是否有效
     */
    @Schema(description = "是否有效 0无效 1有效")
    private Integer available;

    /**
     * 商品sku等级价格列表
     */
    @Schema(description = "商品sku等级价格列表")
    private List<GoodsLevelPriceVO> goodsInfoLevelPrices;

    /**
     * 商品sku客户价格列表
     */
    @Schema(description = "商品sku客户价格列表")
    private List<GoodsCustomerPriceVO> goodsInfoCustomerPrices;

    /**
     * 商品sku订货区间价格列表
     */
    @Schema(description = "商品sku订货区间价格列表")
    private List<GoodsIntervalPriceVO> goodsInfoIntervalPrices;

    /**
     * 商品关联商品模版
     */
    @Schema(description = "商品关联商品模版")
    private GoodsTemplateVO goodsTemplateVO;

}
