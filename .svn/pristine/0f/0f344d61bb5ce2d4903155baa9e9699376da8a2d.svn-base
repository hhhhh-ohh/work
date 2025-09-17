package com.wanmi.sbc.goods.info.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品SKU实体类
 * Created by dyt on 2017/4/11.
 */
@Data
@Entity
@Table(name = "goods_info")
public class  GoodsInfo implements Serializable {

    /**
     * 商品SKU编号
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "goods_info_id")
    private String goodsInfoId;

    /**
     * 老商品SKU编号
     */
    @Column(name = "old_goods_info_id")
    private String oldGoodsInfoId;

    /**
     * 商品编号
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 商品SKU名称
     */
    @Column(name = "goods_info_name")
    private String goodsInfoName;

    /**
     * 商品SKU编码
     */
    @Column(name = "goods_info_no")
    private String goodsInfoNo;

    /**
     * 商品图片
     */
    @Column(name = "goods_info_img")
    @CanEmpty
    private String goodsInfoImg;

    /**
     * 商品条形码
     */
    @Column(name = "goods_info_barcode")
    @CanEmpty
    private String goodsInfoBarcode;

    /**
     * 商品库存
     */
    @Column(name = "stock")
    private Long stock;

    /**
     * 商品参数（VOP）
     */
    @Column(name = "goods_param")
    private String goodsParam;

    /**
     * 商品市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 商品供货价
     */
    @Column(name = "supply_price")
    private BigDecimal supplyPrice;

    /**
     * 建议零售价价
     */
    @Column(name = "retail_price")
    private BigDecimal retailPrice;

    /**
     * 商品成本价
     */
    @CanEmpty
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 上下架时间
     */
    @Column(name = "added_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTime;

    /**
     * 删除标记
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 上下架状态
     */
    @Column(name = "added_flag")
    private Integer addedFlag;

    /**
     * 是否定时上架
     */
    @Column(name = "added_timing_flag")
    private Boolean addedTimingFlag;

    /**
     * 定时上架时间
     */
    @Column(name = "added_timing_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTimingTime;

    /**
     * 是否定时下架
     */
    @Column(name = "takedown_time_flag")
    private Boolean takedownTimeFlag;

    /**
     * 定时下架时间
     */
    @Column(name = "takedown_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime takedownTime;

    /**
     * 公司信息ID
     */
    @Column(name = "company_info_id")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 按客户单独定价
     */
    @Column(name = "custom_flag")
    private Integer customFlag;

    /**
     * 是否可售
     */
    @Column(name = "vendibility")
    private Integer vendibility;

    /**
     * 是否叠加客户等级折扣
     */
    @Column(name = "level_discount_flag")
    private Integer levelDiscountFlag;

    /**
     * 审核状态
     */
    @Enumerated
    @Column(name = "audit_status")
    private CheckStatus auditStatus;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Enumerated
    @Column(name = "company_type")
    private BoolFlag companyType;

    /**
     * 是否独立设价 1:是 0:否
     */
    @Column(name = "alone_flag")
    private Boolean aloneFlag;

    /**
     * 商品详情小程序码
     */
    @Column(name = "small_program_code")
    private String smallProgramCode;

    /**
     * 预估佣金
     */
    @Column(name = "distribution_commission")
    private BigDecimal distributionCommission;

    /**
     * 佣金比例
     */
    @Column(name = "commission_rate")
    private BigDecimal commissionRate;

    /**
     * 分销销量
     */
    @Column(name = "distribution_sales_count")
    private Integer distributionSalesCount;

    /**
     * 分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
     */
    @Column(name = "distribution_goods_audit")
    private DistributionGoodsAudit distributionGoodsAudit;

    /**
     * 分销商品审核不通过或禁止分销原因
     */
    @Column(name = "distribution_goods_audit_reason")
    private String distributionGoodsAuditReason;

    /**
     * 商品一级分类ID
     */
    @Column(name = "cate_top_id")
    private Long cateTopId;

    /**
     * 商品分类ID
     */
    @Column(name = "cate_id")
    private Long cateId;

    /**
     * 品牌ID
     */
    @Column(name = "brand_id")
    private Long brandId;

    /**
     * 销售类型 0:批发, 1:零售
     */
    @Column(name = "sale_type")
    private Integer saleType;

    /**
     * 企业购商品的价格
     */
    @Column(name = "enterprise_price")
    private BigDecimal enterPrisePrice;

    /**
     * 企业购商品审核状态
     */
    @Column(name = "enterprise_goods_audit")
    @Enumerated
    private EnterpriseAuditState enterPriseAuditState;

    /**
     * 企业购商品审核被驳回的原因
     */
    @Column(name = "enterprise_goods_audit_reason")
    private String enterPriseGoodsAuditReason;

    /**
     * 购买积分
     */
    @Column(name = "buy_point")
    private Long buyPoint;

    /**
     * 所属供应商商品skuId
     */
    @Column(name = "provider_goods_info_id")
    private String providerGoodsInfoId;

    /**
     * 供应商Id
     */
    @Column(name = "provider_id")
    private Long providerId;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
     */
    @Column(name = "goods_type")
    private Integer goodsType;


    /**
     * 电子卡券Id
     */
    @Column(name = "electronic_coupons_id")
    private Long electronicCouponsId;

    /**
     * 商品体积 单位：m3
     */
    @Column(name = "goods_cubage")
    private BigDecimal goodsCubage;

    /**
     * 商品重量
     */
    @Column(name = "goods_weight")
    private BigDecimal goodsWeight;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall
     */
    @Column(name = "goods_source")
    private Integer goodsSource;

    /**
     * 第三方平台的skuId
     */
    @Column(name = "third_platform_sku_id")
    private String thirdPlatformSkuId;

    /**
     * 第三方平台的spuId
     */
    @Column(name = "third_platform_spu_id")
    private String thirdPlatformSpuId;

    /**
     * 第三方卖家id
     */
    @Column(name = "seller_id")
    private Long sellerId;

    /**
     * 三方渠道类目id
     */
    @Column(name = "third_cate_id")
    private Long thirdCateId;
    /**
     *三方平台类型，0，linkedmall
     */
    @Column(name = "third_platform_type")
    @Enumerated
    private ThirdPlatformType thirdPlatformType;

    /**
     * 供应商店铺状态 0：关店 1：开店
     */
    @Column(name = "provider_status")
    private Integer providerStatus;

    /**
     * 分销创建时间
     */
    @Column(name = "distribution_create_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime distributionCreateTime;

    /**
     * 商品插件类型
     */
    @Column(name = "plugin_type")
    private PluginType pluginType = PluginType.NORMAL;

    /**
     * 商家类型 0 普通商家 1 跨境商家
     */
    @Column(name = "supplier_type")
    private SupplierType supplierType;

    /**
     * 商家类型,0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Column(name = "store_type")
    private StoreType storeType;

    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Column(name = "is_buy_cycle")
    private Integer isBuyCycle;

    /**
     * 划线价格
     */
    @Column(name = "line_price")
    private BigDecimal linePrice;

    /**
     * 订货号
     */
    @Column(name = "quick_order_no")
    private String quickOrderNo;

    /**
     * 供应商订货号
     */
    @Column(name = "provider_quick_order_no")
    private String providerQuickOrderNo;

    /**
     * 属性尺码
     */
    @Column(name = "attribute_size")
    private String attributeSize;

    /**
     * 0-春秋装 1-夏装 2-冬装
     */
    @Column(name = "attribute_season")
    private Integer attributeSeason;

    /**
     * 0-校服服饰 1-非校服服饰 2-自营产品
     */
    @Column(name = "attribute_goods_type")
    private Integer attributeGoodsType;

    /**
     * 0-老款 1-新款
     */
    @Column(name = "attribute_sale_type")
    private Integer attributeSaleType;

    /**
     * 售卖地区
     */
    @Column(name = "attribute_sale_region")
    private String attributeSaleRegion;

    /**
     * 学段
     */
    @Column(name = "attribute_school_section")
    private String attributeSchoolSection;

    /**
     * 银卡价格
     */
    @Column(name = "attribute_price_silver")
    private BigDecimal attributePriceSilver;

    /**
     * 金卡价格
     */
    @Column(name = "attribute_price_gold")
    private BigDecimal attributePriceGold;

    /**
     * 钻石卡价格
     */
    @Column(name = "attribute_price_diamond")
    private BigDecimal attributePriceDiamond;
    @Column(name = "attribute_price_discount")
    private BigDecimal attributePriceDiscount;
}
