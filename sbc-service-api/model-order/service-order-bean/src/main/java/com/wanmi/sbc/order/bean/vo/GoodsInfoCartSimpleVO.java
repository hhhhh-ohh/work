package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropRelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsInfoCartSimpleVO
 * @description
 * @date 2022/1/13 7:09 下午
 **/
@Data
public class GoodsInfoCartSimpleVO extends BasicResponse {

    /**
     * 商品SKU编号
     */
    @Schema(description = "商品SKU编号")
    private String goodsInfoId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * 商品SKU名称
     */
    @Schema(description = "商品SKU名称")
    private String goodsInfoName;


    /**
     * 商品图片
     */
    @IsImage
    @Schema(description = "商品图片")
    private String goodsInfoImg;


    /**
     * 商品库存
     */
    @Schema(description = "商品库存")
    private Long stock;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    private BigDecimal marketPrice;


    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;


    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型，0、平台自营 1、第三方商家")
    private BoolFlag companyType;

    /**
     * 最新计算的会员价
     * 为空，以市场价为准
     */
    @Schema(description = "最新计算的会员价，为空，以市场价为准")
    private BigDecimal salePrice;

    /**
     * 设价类型 0:按客户 1:按订货量 2:按市场价
     */
    @Schema(description = "设价类型", contentSchema = com.wanmi.sbc.goods.bean.enums.PriceType.class)
    private Integer priceType;

    /**
     * 规格名称规格值 颜色:红色;大小:16G
     */
    @Schema(description = "规格名称规格值 颜色:红色;大小:16G")
    private String specText;

    /**
     * 购买量
     */
    @Schema(description = "购买量")
    private Long buyCount = 0L;

    /**
     * 最新计算的起订量
     * 为空，则不限
     */
    @Schema(description = "最新计算的起订量，为空，则不限")
    private Long startSaleNum;

    /**
     * 最新计算的限定量
     * 为空，则不限
     */
    @Schema(description = "最新计算的限定量，为空，则不限")
    private Long maxCount;

    /**
     * 商品计量单位
     */
    @Schema(description = "商品计量单位")
    private String goodsUnit;

    /**
     * 一对多关系，多个订货区间价格
     */
    @Schema(description = "一对多关系，多个订货区间价格")
    private List<GoodsIntervalPriceVO> intervalPriceList;


    /**
     * 购买积分
     */
    @Schema(description = "购买积分")
    private Long buyPoint;


    /**
     * 商品状态 0：正常 1：缺货 2：失效
     */
    @Schema(description = "商品状态，0：正常 1：缺货 2：失效")
    private GoodsStatus goodsStatus = GoodsStatus.OK;

    /**
     * 促销标签
     */
    @Schema(description = "促销标签")
    private List<MarketingPluginLabelSimpleDetailVO> marketingPluginLabels = new ArrayList<>();

    /**
     * 销售类型 0:批发, 1:零售
     */
    @Schema(description = "销售类型", contentSchema = com.wanmi.sbc.goods.bean.enums.SaleType.class)
    private Integer saleType;

    @Schema(description = "营销插件计算出来的金额")
    private BigDecimal pluginPrice;

    @Schema(description = "插件类型")
    private PluginType pluginType = PluginType.NORMAL;

    @Schema(description = "是否单规格标识 true：单规格")
    private Boolean singleSpecFlag;

    @Schema(description = "降价金额")
    private BigDecimal reductionPrice;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal goodsWeight;
    /**
     * 商品体积 单位：m3
     */
    @Schema(description = "商品体积")
    private BigDecimal goodsCubage;

    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Schema(description = "是否参与周期购 0 否， 1 是")
    private Integer isBuyCycle;

    /**
     * 是否可以原价购买
     * 0 是， 1 否
     */
    @Schema(description = "是否可以原价购买 0 是， 1 否")
    private Integer isBuyForOriginalPrice;

    @Schema(description = "商品属性")
    private List<GoodsPropRelVO> goodsPropRelNests;

    @Schema(description = "订货号")
    private String quickOrderNo;

    @Schema(description = "错误订货号标识")
    private Boolean errorQuickOrderNo;
}