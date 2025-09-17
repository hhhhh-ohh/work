package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;

/**
 * @author zhanggaolei
 */
@Schema
@Data
public class GoodsInfoBaseVO extends BasicResponse {

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
     * 按客户单独定价
     */
    @Schema(description = "按客户单独定价", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer customFlag;

    /**
     * 是否可售
     */
    @Schema(description = "是否可售", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer vendibility;

    /**
     * 是否叠加客户等级折扣
     */
    @Schema(description = "是否叠加客户等级折扣", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer levelDiscountFlag;


    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型，0、平台自营 1、第三方商家")
    private BoolFlag companyType;

    /**
     * 是否独立设价 1:是 0:否
     */
    @Schema(description = "是否独立设价，1:是 0:否")
    private Boolean aloneFlag;

    /**
     * 最新计算的会员价
     * 为空，以市场价为准   TODO 后续待确认是否删除
     */
//    @Schema(description = "最新计算的会员价，为空，以市场价为准")
//    private BigDecimal salePrice;

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
//    @Schema(description = "最新计算的起订量，为空，则不限")
//    private Long count;

    /**
     * 最新计算的起订量
     * 为空，则不限
     */
    @Schema(description = "起订量，为空，则不限")
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
     * 有效状态 0:无效,1:有效
     */
    @Schema(description = "有效状态", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer validFlag;

    /**
     * 商品一级分类ID
     */
    @Schema(description = "商品一级分类ID")
    private Long cateTopId;

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
     * 多对多关系，多个店铺分类编号
     */
    @Schema(description = "多对多关系，多个店铺分类编号")
    private List<Long> storeCateIds;

    /**
     * 预估佣金
     */
    @Schema(description = "预估佣金")
    private BigDecimal distributionCommission;

    /**
     * 佣金比例
     */
    @Schema(description = "佣金比例")
    private BigDecimal commissionRate;

    /**
     * 分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
     */
    @Schema(description = "分销商品审核状态")
    private DistributionGoodsAudit distributionGoodsAudit;

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
    private List<MarketingPluginLabelDetailVO> marketingPluginLabels = new ArrayList<>();

    /**
     * 销售类型 0:批发, 1:零售
     */
    @Schema(description = "销售类型", contentSchema = com.wanmi.sbc.goods.bean.enums.SaleType.class)
    private Integer saleType;

    /**
     * 是否允许独立设价 0:不允许, 1:允许
     */
    @Schema(description = "是否允许独立设价", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer allowPriceSet;

    /**
     * 企业购商品的价格
     */
    @Schema(description = "企业购商品的销售价格")
    private BigDecimal enterPrisePrice;

    /**
     * 企业购商品的审核状态
     */
    @Schema(description = "企业购商品的审核状态", contentSchema = com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState.class)
    private EnterpriseAuditState enterPriseAuditState;

    /**
     * 所属供应商商品skuId
     */
    @Schema(description = "所属供应商商品skuId")
    private String providerGoodsInfoId;

    /**
     * 供应商Id
     */
    @Schema(description = "供应商Id")
    private Long providerId;

    /**
     * 第三方平台的spuId
     */
    @Schema(description = "第三方平台的spuId")
    private String thirdPlatformSpuId;

    /**
     * 第三方平台的skuId
     */
    @Schema(description = "第三方平台的skuId")
    private String thirdPlatformSkuId;

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

    @Schema(description = "插件类型")
    private PluginType pluginType = PluginType.NORMAL;

    /**
     * 商品重量
     */
    private BigDecimal goodsWeight;
    /**
     * 商品体积 单位：m3
     */
    private BigDecimal goodsCubage;
    /**
     * 运费模板ID
     */
    @Schema(description = "运费模板ID")
    private Long freightTempId;

    private BigDecimal supplyPrice;

    private String goodsInfoNo;

    private Boolean singleSpecFlag;

    private DeleteFlag delFlag;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券'")
    private Integer goodsType;


    /**
     * 电子卡券Id
     */
    @Schema(description = "电子卡券Id")
    private Long electronicCouponsId;

    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Schema(description = "是否参与周期购 0 否， 1 是")
    private Integer isBuyCycle;

    /**
     * 订货号
     */
    @Schema(description = "订货号")
    private String quickOrderNo;

    @Schema(description = "商品属性")
    List<GoodsPropRelVO> goodsPropRelNests;

    public Integer getVendibility(){
        if (Objects.nonNull(providerGoodsInfoId)) {
            //供应商商品可售（商品上架、未删除、已审核，店铺开店）
            if((Objects.nonNull(vendibility) && DefaultFlag.YES.toValue() == vendibility)
                    && Constants.yes.equals(providerStatus)){
                return Constants.yes;
            } else {
                return Constants.no;
            }
        }
        return Constants.yes;
    }



}
