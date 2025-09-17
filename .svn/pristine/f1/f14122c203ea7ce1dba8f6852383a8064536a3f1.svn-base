package com.wanmi.sbc.goods.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsInfoDetailSimpleMarketingLabelVO
 * @description TODO
 * @date 2021/10/28 3:36 下午
 **/
@Data
public class GoodsInfoDetailSimpleMarketingLabelVO extends BasicResponse {
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
     * 商品SKU编码
     */
    @Schema(description = "商品SKU编码")
    private String goodsInfoNo;

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
     * 是否可售
     */
    @Schema(description = "是否可售", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer vendibility;

    /**
     * 设价类型 0:按客户 1:按订货量 2:按市场价
     */
    @Schema(description = "设价类型", contentSchema = com.wanmi.sbc.goods.bean.enums.PriceType.class)
    private Integer priceType;

    /**
     * 新增时，模拟多个规格值 ID
     * 查询详情返回响应，扁平化多个规格值ID
     */
    @Schema(description = "新增时，模拟多个规格值 ID，查询详情返回响应，扁平化多个规格值ID")
    private List<Long> mockSpecDetailIds;



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
    private Long count;

    /**
     * 最新计算的限定量
     * 为空，则不限
     */
    @Schema(description = "最新计算的限定量，为空，则不限")
    private Long maxCount;

    /**
     * 一对多关系，多个订货区间价格编号
     */
    @Schema(description = "一对多关系，多个订货区间价格编号")
    private List<Long> intervalPriceIds;

    /**
     * 规格名称规格值 颜色:红色;大小:16G
     */
    @Schema(description = "规格名称规格值", example = "颜色:红色;大小:16G")
    private String specText;

    /**
     * 最小区间价
     */
    @Schema(description = "最小区间价")
    private BigDecimal intervalMinPrice;

    /**
     * 最大区间价
     */
    @Schema(description = "最大区间价")
    private BigDecimal intervalMaxPrice;


    /**
     * 商品分类ID
     */
    @Schema(description = "商品分类ID")
    private Long cateId;

    /**
     * 品牌ID
     */
    @Schema(description = "品牌ID")
    private Long brandId;


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
    private List<MarketingPluginSimpleLabelVO> marketingPluginLabels = new ArrayList<>();


    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String cateName;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 商品来源，0供应商，1商家 2linkedMall
     */
    @Schema(description = "商品来源，0供应商，1商家 2linkedMall")
    private Integer goodsSource;

    /**
     *三方平台类型，0，linkedmall
     */
    @Schema(description = "三方平台类型，0，linkedmall")
    private ThirdPlatformType thirdPlatformType;

    /**
     * 供应商店铺状态 0：关店 1：开店
     */
    @Schema(description = "供应商店铺状态 0：关店 1：开店")
    private Integer providerStatus;

    @Schema(description = "营销插件计算出来的金额")
    private BigDecimal pluginPrice;

    @Schema(description = "sku起售数量")
    private Long startSaleNum;

    /**
     * 订货号
     */
    @Schema(description = "订货号")
    private String quickOrderNo;

    /**
     * 供应商订货号
     */
    @Schema(description = "供应商订货号")
    private String providerQuickOrderNo;
}
