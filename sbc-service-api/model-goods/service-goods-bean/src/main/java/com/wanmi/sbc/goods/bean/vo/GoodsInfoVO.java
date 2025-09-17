package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品SKU实体类
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoVO extends BasicResponse {

    public GoodsInfoVO(String goodsInfoId, String goodsId, Long stock) {
        this.goodsInfoId = goodsInfoId;
        this.goodsId = goodsId;
        this.stock = stock;
    }

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
     * 商品副标题
     */
    @Schema(description = "商品副标题")
    private String goodsSubtitle;

    private String catePath;

    private String wechatCatePath;

    private Boolean wechatAdded = false;

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
     * 商品条形码
     */
    @Schema(description = "商品条形码")
    private String goodsInfoBarcode;

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
     * 供货价
     */
    @Schema(description = "供货价")
    @CanEmpty
    private BigDecimal supplyPrice;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

    /**
     * 建议零售价
     */
    @Schema(description = "建议零售价")
    @CanEmpty
    private BigDecimal retailPrice;

    /**
     * 拼团价
     */
    @Schema(description = "拼团价")
    private BigDecimal grouponPrice;

    /**
     * 商品成本价
     */
    @Schema(description = "商品成本价")
    private BigDecimal costPrice;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 上下架时间
     */
    @Schema(description = "上下架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTime;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记 0: 否, 1: 是")
    private DeleteFlag delFlag;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private Integer addedFlag;

    /**
     * 是否定时上架
     */
    @Schema(description = "是否定时上架 true:是,false:否")
    private Boolean addedTimingFlag;

    /**
     * 定时上架时间
     */
    @Schema(description = "定时上架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTimingTime;

    /**
     * 是否定时下架
     */
    private Boolean takedownTimeFlag;

    /**
     * 定时下架时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime takedownTime;

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
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

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
     * 审核状态
     */
    @Schema(description = "审核状态")
    private CheckStatus auditStatus;

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
     * 新增时，模拟多个规格ID
     * 查询详情返回响应，扁平化多个规格ID
     */
    @Schema(description = "新增时，模拟多个规格ID，查询详情返回响应，扁平化多个规格ID")
    private List<Long> mockSpecIds;

    private String mockSpecName;

    /**
     * 新增时，模拟多个规格值 ID
     * 查询详情返回响应，扁平化多个规格值ID
     */
    @Schema(description = "新增时，模拟多个规格值 ID，查询详情返回响应，扁平化多个规格值ID")
    private List<Long> mockSpecDetailIds;

    private String mockSpecDetailName;

    /**
     * 商品分页，扁平化多个商品规格值ID
     */
    @Schema(description = "商品分页，扁平化多个商品规格值ID")
    private List<Long> specDetailRelIds;

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
     * 分销销量
     */
    @Schema(description = "分销销量")
    private Integer distributionSalesCount;

    /**
     * 分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
     */
    @Schema(description = "分销商品审核状态")
    private DistributionGoodsAudit distributionGoodsAudit;

    /**
     * 分销商品审核不通过或禁止分销原因
     */
    @Schema(description = "分销商品审核不通过或禁止分销原因")
    private String distributionGoodsAuditReason;

    /**
     * 分销创建时间
     */
    @Schema(description = "分销创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime distributionCreateTime;

    /**
     * 购买积分
     */
    @Schema(description = "购买积分")
    private Long buyPoint;

    /**
     * 促销标签
     */
    @Schema(description = "促销标签")
    private List<MarketingPluginSimpleLabelVO> marketingPluginLabels = new ArrayList<>();

    /**
     * 前端是否选中
     */
    @Schema(description = "前端是否选中")
    private Boolean checked = Boolean.FALSE;

    /**
     * 商品状态 0：正常 1：缺货 2：失效
     */
    @Schema(description = "商品状态，0：正常 1：缺货 2：失效")
    private GoodsStatus goodsStatus = GoodsStatus.OK;

    /**
     * 计算单位
     */
    @Schema(description = "计算单位")
    private String goodsUnit;

    /**
     * 促销标签
     */
    @Schema(description = "促销标签")
    private List<MarketingLabelVO> marketingLabels = new ArrayList<>();

    /**
     * 拼团标签
     */
    @Schema(description = "促销标签")
    private GrouponLabelVO grouponLabel;
    /**
     * 优惠券标签
     */
    @Schema(description = "优惠券标签")
    private List<CouponLabelVO> couponLabels = new ArrayList<>();

    /**
     * 商品体积 单位：m3
     */
    @Schema(description = "商品体积，单位：m3")
    private BigDecimal goodsCubage;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal goodsWeight;

    /**
     * 运费模板ID
     */
    @Schema(description = "运费模板ID")
    private Long freightTempId;

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
     * 商品详情小程序码
     */
    @Schema(description = "商品详情小程序码")
    private String smallProgramCode;

    /**
     * 是否已关联分销员
     */
    @Schema(description = "是否已关联分销员，0：否，1：是")
    private Integer joinDistributior;

    /**
     * 商品评论数
     */
    @Schema(description = "商品评论数")
    private Long goodsEvaluateNum;

    /**
     * 商品收藏量
     */
    @Schema(description = "商品收藏量")
    private Long goodsCollectNum;

    /**
     * 商品销量
     */
    @Schema(description = "商品销量")
    private Long goodsSalesNum;

    /**
     * 商品好评数
     */
    @Schema(description = "商品好评数")
    private Long goodsFavorableCommentNum;

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
     * 企业购商品审核被驳回的原因
     */
    @Schema(description = "企业购商品审核被驳回的原因")
    private String enterPriseGoodsAuditReason;

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
     * 预售价格
     */
    @Schema(description = "预售价格")
    private BigDecimal bookingPrice;

    /**
     * 预约价格
     */
    @Schema(description = "预约价格")
    private BigDecimal appointmentPrice;

    /**
     * 预售活动信息
     */
    @Schema(description = "预售活动信息")
    private BookingSaleVO bookingSaleVO;

    @Schema(description = "是否预售商品")
    private Boolean bookingSaleFlag;


    /**
     * 预约活动信息
     */
    @Schema(description = "预约活动信息")
    private AppointmentSaleVO appointmentSaleVO;

    @Schema(description = "是否预约商品")
    private Boolean appointmentSaleFlag;

    private GoodsVO goods;

    /**
     * 所属供应商商品skuId
     */
    @Schema(description = "所属供应商商品skuId")
    private String providerGoodsInfoId;

    /**
     * 所属供应商商品sku编码
     */
    @Schema(description = "所属供应商商品sku编码")
    private String providerGoodsInfoNo;

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

    /**
     * 店铺分类名称，逗号隔开
     */
    @Schema(description = "店铺分类名称，逗号隔开")
    private String storeCateName;

    /**
     * 营销商品状态
     */
    @Schema(description = "营销商品状态")
    private MarketingGoodsStatus marketingGoodsStatus;


    /**
     * 商品市场价
     */
    @Schema(description = "O2o商品市场价")
    private BigDecimal storeMarketPrice;

    public Integer getVendibility(){
        return getVendibility(false);
    }

    public Integer getVendibility(boolean useOriginal){
        if(useOriginal){
            return this.vendibility;
        }
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

    /**
     * 单品选择的营销
     */
    private GoodsMarketingVO goodsMarketing;

    /**
     * 规格值关联列表
     */
    private List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelList;

    /**
     * 级别价列表
     */
    private List<GoodsLevelPriceVO> goodsLevelPriceList;

    /**
     * 区间价列表
     */
    private List<GoodsIntervalPriceVO> intervalPriceList;

    @Schema(description = "商品类型")
    private PluginType pluginType;

    /**
     * 跨境商品信息
     */
    @Schema(description = "跨境商品信息")
    private Object extendedAttributes;


    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;

    /**
     * 删除标记
     */
    @Schema(description = "新增标记，0: 否, 1: 是")
    private NewAdd isNewAdd;

    @Schema(description = "sku起售数量")
    private Long startSaleNum;

    @Schema(description = "加价比例")
    private BigDecimal addRate;

    /**
     * 是否限售地区
     */
    @Schema(description = "是否限售地区")
    private Boolean restrictedAddressFlag = Boolean.FALSE;

    /**
     * 老商品SKU编号
     */
    @Schema(description = "老商品SKU编号")
    private String oldGoodsInfoId;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 电子卡券ID
     */
    @Schema(description = "电子卡券ID")
    private Long electronicCouponsId;

    /**
     * 电子卡券名称
     */
    @Schema(description = "电子卡券名称")
    private String electronicCouponsName;

    /**
     * 商品SKU编码
     */
    @Schema(description = "商品SPU编码")
    private String goodsNo;

    /**
     * 起售数量
     */
    @Schema(description = "起售数量")
    private Long startSellingNum;

    /**
     * 限购数量
     */
    @Schema(description = "限购数量")
    private Long limitSellingNum;

    /**
     * 三方渠道类目id
     */
    @Schema(description = "三方渠道类目id")
    private Long thirdCateId;

    /**
     * 付费会员标签
     */
    @Schema(description = "付费会员标签")
    private String payMemberLabel;

    /**
     * 付费会员价
     */
    @Schema(description = "付费会员价")
    private BigDecimal payMemberPrice;

    /**
     * 是否拥有付费会员
     */
    @Schema(description = "是否拥有付费会员")
    private Boolean payMemberOwnFlag = Boolean.FALSE;

    /**
     * 不可售原因
     */
    @Schema(description = "不可售原因")
    private VendibilityReason vendibilityReason;


    /**
     * 新人购 前端是否可以选择
     */
    @Schema(description = "新人购 前端是否可以选择")
    private Boolean disabled;


    /**
     * 绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
     */
    @Schema(description = "绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败")
    private Integer bindState;

    /**
     * 拼团生效标识
     */
    @Schema(description = "拼团生效标识，true生效")
    private Boolean groupStatus;


    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Schema(description = "是否参与周期购，0 否， 1 是")
    private Integer isBuyCycle;

    /**
     * 商品划线价
     */
    @Schema(description = "商品划线价")
    private BigDecimal linePrice;

    /**
     * 商家sku预警值
     */
    @Schema(description = "商家sku预警值")
    private Long warningStock;

    /**
     * 预警标志
     */
    @Schema(description = "商家sku预警值")
    private Boolean showStockOutFlag;

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

    @Schema(description = "属性尺码")
    private String attributeSize;

    /**
     * 0-春秋装 1-夏装 2-冬装
     */
    @Schema(description = "款式季节")
    private Integer attributeSeason;

    /**
     * 0-校服服饰 1-非校服服饰 2-自营产品
     */
    @Schema(description = "是否校服")
    private Integer attributeGoodsType;

    /**
     * 0-老款 1-新款
     */
    @Schema(description = "新老款")
    private Integer attributeSaleType;

    /**
     * 售卖地区
     */
    @Schema(description = "售卖地区")
    private String attributeSaleRegion;

    /**
     * 学段
     */
    @Schema(description = "学段")
    private String attributeSchoolSection;

    /**
     * 银卡价格
     */
    @Schema(description = "银卡价格")
    private BigDecimal attributePriceSilver;

    /**
     * 金卡价格
     */
    @Schema(description = "金卡价格")
    private BigDecimal attributePriceGold;

    /**
     * 钻石卡价格
     */
    @Schema(description = "钻石卡价格")
    private BigDecimal attributePriceDiamond;

    @Schema(description = "折扣价格")
    private BigDecimal attributePriceDiscount;
}