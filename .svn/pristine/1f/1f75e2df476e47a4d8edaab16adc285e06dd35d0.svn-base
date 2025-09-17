package com.wanmi.sbc.goods.goodsaudit.model.root;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>商品审核实体类</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Data
@Entity
@Table(name = "goods_audit")
public class GoodsAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * goodsId
	 */
	@Id
	@Column(name = "goods_id")
	private String goodsId;

	/**
	 * 旧商品Id
	 */
	@Column(name = "old_goods_id")
	private String oldGoodsId;

	/**
	 * 分类编号
	 */
	@Column(name = "cate_id")
	private Long cateId;

	/**
	 * 销售类别
	 */
	@Column(name = "sale_type")
	private Integer saleType;

	/**
	 * 品牌编号
	 */
	@Column(name = "brand_id")
	@CanEmpty
	private Long brandId;

	/**
	 * 商品名称
	 */
	@Column(name = "goods_name")
	private String goodsName;

	/**
	 * 商品副标题
	 */
	@Column(name = "goods_subtitle")
	private String goodsSubtitle;

	/**
	 * SPU编码
	 */
	@Column(name = "goods_no")
	private String goodsNo;

	/**
	 * 计量单位
	 */
	@Column(name = "goods_unit")
	@CanEmpty
	private String goodsUnit;

	/**
	 * 商品主图
	 */
	@Column(name = "goods_img")
	@CanEmpty
	private String goodsImg;

	/**
	 * 商品视频地址
	 */
	@Column(name = "goods_video")
	@CanEmpty
	private String goodsVideo;

	/**
	 * 商品重量
	 */
	@Column(name = "goods_weight")
	private BigDecimal goodsWeight;

	/**
	 * 市场价
	 */
	@Column(name = "market_price")
	@CanEmpty
	private BigDecimal marketPrice;

	/**
	 * 供货价
	 */
	@Column(name = "supply_price")
	@CanEmpty
	private BigDecimal supplyPrice;

	/**
	 * 建议零售价
	 */
	@Column(name = "recommended_retail_price")
	@CanEmpty
	private BigDecimal recommendedRetailPrice;

	/**
	 * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
	 */
	@Column(name = "goods_type")
	private Integer goodsType;

	/**
	 * 划线价格
	 */
	@Column(name = "line_price")
	@CanEmpty
	private BigDecimal linePrice;

	/**
	 * 成本价
	 */
	@Column(name = "cost_price")
	@CanEmpty
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
	 * 商品来源，0供应商，1商家,2 linkedmall
	 */
	@Column(name = "goods_source")
	private Integer goodsSource;

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
	 * 是否多规格标记
	 */
	@Column(name = "more_spec_flag")
	private Integer moreSpecFlag;

	/**
	 * 设价类型 0:按客户 1:按订货量 2:按市场价
	 */
	@Column(name = "price_type")
	private Integer priceType;

	/**
	 * 是否按客户单独定价
	 */
	@Column(name = "custom_flag")
	private Integer customFlag;

	/**
	 * 订货量设价时,是否允许sku独立设阶梯价(0:不允许,1:允许)
	 */
	@Column(name = "allow_price_set")
	private Integer allowPriceSet;

	/**
	 * 是否叠加客户等级折扣
	 */
	@Column(name = "level_discount_flag")
	private Integer levelDiscountFlag;

	/**
	 * 公司信息ID
	 */
	@Column(name = "company_info_id")
	private Long companyInfoId;

	/**
	 * 公司名称
	 */
	@Column(name = "supplier_name")
	private String supplierName;


	/**
	 * 店铺ID
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 提交审核时间
	 */
	@Column(name = "submit_time")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime submitTime;

	/**
	 * 审核状态
	 */
	@Column(name = "audit_status")
	@Enumerated
	private CheckStatus auditStatus;

	/**
	 * 审核驳回原因
	 */
	@Column(name = "audit_reason")
	private String auditReason;

	/**
	 * 商品详情
	 */
	@Column(name = "goods_detail")
	private String goodsDetail;

	/**
	 * 商品移动端详情
	 */
	@Column(name = "goods_mobile_detail")
	private String goodsMobileDetail;

	/**
	 * 库存，根据相关所有SKU库存来合计
	 */
	@Column(name = "stock")
	private Long stock;

	/**
	 * 商家类型 0、平台自营 1、第三方商家
	 */
	@Column(name = "company_type")
	@Enumerated
	private BoolFlag companyType;

	/**
	 * 商品体积 单位：m3
	 */
	@Column(name = "goods_cubage")
	private BigDecimal goodsCubage;

	/**
	 * 运费模板ID
	 */
	@Column(name = "freight_temp_id")
	private Long freightTempId;

	/**
	 * 运费模板名称
	 */
	@Transient
	private String freightTempName;

	/**
	 * 商品评论数
	 */
	@Column(name = "goods_evaluate_num")
	private Long goodsEvaluateNum;

	/**
	 * 商品收藏量
	 */
	@Column(name = "goods_collect_num")
	private Long goodsCollectNum;

	/**
	 * 商品销量
	 */
	@Column(name = "goods_sales_num")
	private Long goodsSalesNum;

	/**
	 * 商品好评数量
	 */
	@Column(name = "goods_favorable_comment_num")
	private Long goodsFavorableCommentNum;

	/**
	 * 注水销量
	 */
	@Column(name = "sham_sales_num")
	private Long shamSalesNum;

	/**
	 * 排序号
	 */
	@Column(name = "sort_no")
	private Long sortNo;

	/**
	 * 是否单规格
	 */
	@Column(name = "single_spec_flag")
	private Boolean singleSpecFlag;

	/**
	 * 最小市场价（取自SKU中最小的价格）
	 */
	@Column(name = "sku_min_market_price")
	private BigDecimal skuMinMarketPrice;

	/**
	 * 购买方式 0立即购买,1购物车,内容以,相隔
	 */
	@Column(name = "goods_buy_types")
	private String goodsBuyTypes;

	/**
	 * 供应商名称
	 */
	@Column(name = "provider_name")
	private String providerName;

	/**
	 * 供应商id
	 */
	@Column(name = "provider_id")
	private Long providerId;

	/**
	 * 所属供应商商品Id
	 */
	@Column(name = "provider_goods_id")
	private String providerGoodsId;

	/**
	 * 是否需要同步 0：不需要同步 1：需要同步
	 */
	@Column(name = "need_synchronize")
	@Enumerated
	private BoolFlag needSynchronize;

	/**
	 * 删除原因
	 */
	@Column(name = "delete_reason")
	private String deleteReason;

	/**
	 * 下架原因
	 */
	@Column(name = "add_false_reason")
	private String addFalseReason;

	/**
	 * 是否可售
	 */
	@Column(name = "vendibility")
	private Integer vendibility;
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
	 * 虚拟字段，好评率
	 */
	@Formula("goods_favorable_comment_num / goods_evaluate_num")
	private BigDecimal feedbackRate;

	/**
	 * 虚拟字段，全销售量 （真实销售量 + 注水销量）
	 */
	@Formula("goods_sales_num + sham_sales_num")
	private Long allSalesNum;

	/**
	 * 供应商店铺状态 0：关店 1：开店
	 */
	@Column(name = "provider_status")
	private Integer providerStatus;

	/**
	 * 标签id，以逗号拼凑
	 */
	@Column(name = "label_id_str")
	private String labelIdStr;

	/**
	 * 商品类型；0:一般商品 1:跨境商品
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
	 * 在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置
	 * 0 否， 1 是
	 */
	@Column(name = "is_independent")
	private EnableStatus isIndependent;

	/**
	 * 审核类型 1:初次审核 2:二次审核
	 */
	@Column(name = "audit_type")
	private Integer auditType;

	public String getGoodsId() {
		if (StringUtils.isEmpty(this.goodsId)) {
			this.goodsId = UUIDUtil.getUUID();
		}
		return goodsId;
	}
}
