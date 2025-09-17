package com.wanmi.sbc.goods.api.request.goodsaudit;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品审核通用查询请求参数</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAuditQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-goodsIdList
	 */
	@Schema(description = "批量查询-goodsIdList")
	private List<String> goodsIdList;

	/**
	 * goodsId
	 */
	@Schema(description = "goodsId")
	private String goodsId;

	/**
	 * 旧商品Id
	 */
	@Schema(description = "旧商品Id")
	private String oldGoodsId;

	/**
	 * 销售类别(0:批发,1:零售)
	 */
	@Schema(description = "销售类别(0:批发,1:零售)")
	private Integer saleType;

	/**
	 * 商品分类Id
	 */
	@Schema(description = "商品分类Id")
	private Long cateId;

	/**
	 * 品牌Id
	 */
	@Schema(description = "品牌Id")
	private Long brandId;

	/**
	 * goodsName
	 */
	@Schema(description = "goodsName")
	private String goodsName;

	/**
	 * goodsSubtitle
	 */
	@Schema(description = "goodsSubtitle")
	private String goodsSubtitle;

	/**
	 * goodsNo
	 */
	@Schema(description = "goodsNo")
	private String goodsNo;

	/**
	 * likeGoodsNo
	 */
	@Schema(description = "likeGoodsNo")
	private String likeGoodsNo;

	/**
	 * 非OldGoodsId
	 */
	private String notOldGoodsId;

	/**
	 * goodsUnit
	 */
	@Schema(description = "goodsUnit")
	private String goodsUnit;

	/**
	 * goodsImg
	 */
	@Schema(description = "goodsImg")
	private String goodsImg;

	/**
	 * goodsVideo
	 */
	@Schema(description = "goodsVideo")
	private String goodsVideo;

	/**
	 * 商品重量
	 */
	@Schema(description = "商品重量")
	private BigDecimal goodsWeight;

	/**
	 * 商品体积
	 */
	@Schema(description = "商品体积")
	private BigDecimal goodsCubage;

	/**
	 * 单品运费模板id
	 */
	@Schema(description = "单品运费模板id")
	private Long freightTempId;

	/**
	 * 市场价
	 */
	@Schema(description = "市场价")
	private BigDecimal marketPrice;

	/**
	 * 供货价
	 */
	@Schema(description = "供货价")
	private BigDecimal supplyPrice;

	/**
	 * 建议零售价
	 */
	@Schema(description = "建议零售价")
	private BigDecimal retailPrice;

	/**
	 * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
	 */
	@Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
	private Integer goodsType;

	/**
	 * 划线价
	 */
	@Schema(description = "划线价")
	private BigDecimal linePrice;

	/**
	 * 成本价
	 */
	@Schema(description = "成本价")
	private BigDecimal costPrice;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 搜索条件:上下架时间开始
	 */
	@Schema(description = "搜索条件:上下架时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime addedTimeBegin;
	/**
	 * 搜索条件:上下架时间截止
	 */
	@Schema(description = "搜索条件:上下架时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime addedTimeEnd;

	/**
	 * 商品来源，0供应商，1商家,2linkedmall
	 */
	@Schema(description = "商品来源，0供应商，1商家,2linkedmall")
	private Integer goodsSource;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private Integer delFlag;

	/**
	 * 上下架状态,0:下架1:上架2:部分上架
	 */
	@Schema(description = "上下架状态,0:下架1:上架2:部分上架")
	private Integer addedFlag;

	/**
	 * 规格类型,0:单规格1:多规格
	 */
	@Schema(description = "规格类型,0:单规格1:多规格")
	private Integer moreSpecFlag;

	/**
	 * 设价类型,0:按客户1:按订货量2:按市场价
	 */
	@Schema(description = "设价类型,0:按客户1:按订货量2:按市场价")
	private Integer priceType;

	/**
	 * 订货量设价时,是否允许sku独立设阶梯价(0:不允许,1:允许)
	 */
	@Schema(description = "订货量设价时,是否允许sku独立设阶梯价(0:不允许,1:允许)")
	private Integer allowPriceSet;

	/**
	 * 按客户单独定价,0:否1:是
	 */
	@Schema(description = "按客户单独定价,0:否1:是")
	private Integer customFlag;

	/**
	 * 叠加客户等级折扣，0:否1:是
	 */
	@Schema(description = "叠加客户等级折扣，0:否1:是")
	private Integer levelDiscountFlag;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	private Long storeId;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID")
	private Long companyInfoId;

	/**
	 * supplierName
	 */
	@Schema(description = "supplierName")
	private String supplierName;

	/**
	 * 搜索条件:提交审核时间开始
	 */
	@Schema(description = "搜索条件:提交审核时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime submitTimeBegin;
	/**
	 * 搜索条件:提交审核时间截止
	 */
	@Schema(description = "搜索条件:提交审核时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime submitTimeEnd;

	/**
	 * 审核状态,0:未审核1 审核通过2审核失败3禁用中
	 */
	@Schema(description = "审核状态,0:未审核1 审核通过2审核失败3禁用中")
	private Integer auditStatus;

	/**
	 * auditReason
	 */
	@Schema(description = "auditReason")
	private String auditReason;

	/**
	 * 商品详情
	 */
	@Schema(description = "商品详情")
	private String goodsDetail;

	/**
	 * 移动端图文详情
	 */
	@Schema(description = "移动端图文详情")
	private String goodsMobileDetail;

	/**
	 * 自营标识
	 */
	@Schema(description = "自营标识")
	private BoolFlag companyType;

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
	 * 商品好评数量
	 */
	@Schema(description = "商品好评数量")
	private Long goodsFavorableCommentNum;

	/**
	 * 注水销量
	 */
	@Schema(description = "注水销量")
	private Long shamSalesNum;

	/**
	 * 排序号
	 */
	@Schema(description = "排序号")
	private Long sortNo;

	/**
	 * 0:多规格1:单规格
	 */
	@Schema(description = "0:多规格1:单规格")
	private Integer singleSpecFlag;

	/**
	 * 是否定时上架 0:否1:是
	 */
	@Schema(description = "是否定时上架 0:否1:是")
	private Integer addedTimingFlag;

	/**
	 * 搜索条件:定时上架时间开始
	 */
	@Schema(description = "搜索条件:定时上架时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime addedTimingTimeBegin;
	/**
	 * 搜索条件:定时上架时间截止
	 */
	@Schema(description = "搜索条件:定时上架时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime addedTimingTimeEnd;

	/**
	 * 库存（准实时）
	 */
	@Schema(description = "库存（准实时）")
	private Long stock;

	/**
	 * 最小市场价
	 */
	@Schema(description = "最小市场价")
	private BigDecimal skuMinMarketPrice;

	/**
	 * goodsBuyTypes
	 */
	@Schema(description = "goodsBuyTypes")
	private String goodsBuyTypes;

	/**
	 * providerGoodsId
	 */
	@Schema(description = "providerGoodsId")
	private String providerGoodsId;

	/**
	 * 供应商Id
	 */
	@Schema(description = "供应商Id")
	private Long providerId;

	/**
	 * providerName
	 */
	@Schema(description = "providerName")
	private String providerName;

	/**
	 * 建议零售价
	 */
	@Schema(description = "建议零售价")
	private BigDecimal recommendedRetailPrice;

	/**
	 * 是否需要同步 0：不需要同步 1：需要同步
	 */
	@Schema(description = "是否需要同步 0：不需要同步 1：需要同步")
	private Integer needSynchronize;

	/**
	 * deleteReason
	 */
	@Schema(description = "deleteReason")
	private String deleteReason;

	/**
	 * addFalseReason
	 */
	@Schema(description = "addFalseReason")
	private String addFalseReason;

	/**
	 * 是否可售，0不可售，1可售
	 */
	@Schema(description = "是否可售，0不可售，1可售")
	private Integer vendibility;

	/**
	 * thirdPlatformSpuId
	 */
	@Schema(description = "thirdPlatformSpuId")
	private String thirdPlatformSpuId;

	/**
	 * 第三方卖家id
	 */
	@Schema(description = "第三方卖家id")
	private Long sellerId;

	/**
	 * 三方渠道类目id
	 */
	@Schema(description = "三方渠道类目id")
	private Long thirdCateId;

	/**
	 * 三方平台类型，0，linkedmall
	 */
	@Schema(description = "三方平台类型，0，linkedmall")
	private Integer thirdPlatformType;

	/**
	 * 供应商状态 0: 关店 1:开店
	 */
	@Schema(description = "供应商状态 0: 关店 1:开店")
	private Integer providerStatus;

	/**
	 * 标签id，以逗号拼凑
	 */
	@Schema(description = "标签id，以逗号拼凑")
	private String labelId;

	/**
	 * 商品类型；0:一般商品 1:跨境商品
	 */
	@Schema(description = "商品类型；0:一般商品 1:跨境商品")
	private Integer pluginType;

	/**
	 * 商家类型 0 普通商家,1 跨境商家
	 */
	@Schema(description = "商家类型 0 普通商家,1 跨境商家")
	private Integer supplierType;

	/**
	 * 商家类型：0供应商，1商家，2：O2O商家，3：跨境商家
	 */
	@Schema(description = "商家类型：0供应商，1商家，2：O2O商家，3：跨境商家")
	private Integer storeType;

	/**
	 * 商家端编辑供应商商品判断页面是否是独立设置价格 0 ：否  1：是
	 */
	@Schema(description = "商家端编辑供应商商品判断页面是否是独立设置价格 0 ：否  1：是")
	private Integer isIndependent;

	/**
	 * 审核类型 1:初次审核 2:二次审核
	 */
	@Schema(description = "审核类型 1:初次审核 2:二次审核")
	private Integer auditType;

	/**
	 * 店铺分类Id
	 */
	private Long storeCateId;

	/**
	 * 店铺分类所关联的SpuIds
	 */
	private List<String> storeCateGoodsIds;

	/**
	 * 模糊条件-SKU编码
	 */
	private String likeGoodsInfoNo;

	/**
	 * 批量商品分类
	 */
	private List<Long> cateIds;

	/**
	 * 非商品Id
	 */
	private String notGoodsId;

	/**
	 * 商品来源，0品牌商城，1商家
	 */
	@Schema(description = "商品来源，0品牌商城，1商家")
	private GoodsSource searchGoodsSource;
}