package com.wanmi.sbc.goods.api.request.livegoods;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;

import java.util.Collection;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>直播商品通用查询请求参数</p>
 * @author zwb
 * @date 2020-06-10 11:05:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键idList
	 */
	@Schema(description = "批量查询-主键idList")
	private List<Long> idList;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;


	/**
	 * 微信商品id
	 */
	@Schema(description = "微信商品id")
	private Long goodsId;
	/**
	 * 商品标题
	 */
	@Schema(description = "商品标题")
	private String name;

	/**
	 * 填入mediaID
	 */
	@Schema(description = "填入mediaID")
	private String coverImgUrl;

	/**
	 * 价格类型，1：一口价，2：价格区间，3：显示折扣价
	 */
	@Schema(description = "价格类型，1：一口价，2：价格区间，3：显示折扣价")
	private Integer priceType;

	/**
	 * 直播商品价格左边界
	 */
	@Schema(description = "直播商品价格左边界")
	private BigDecimal price;

	/**
	 * 直播商品价格右边界
	 */
	@Schema(description = "直播商品价格右边界")
	private BigDecimal price2;

	/**
	 * 商品详情页的小程序路径
	 */
	@Schema(description = "商品详情页的小程序路径")
	private String url;

	/**
	 * 库存
	 */
	@Schema(description = "库存")
	private Long stock;

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
	 * 商品详情id
	 */
	@Schema(description = "商品详情id")
	private String goodsInfoId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	private Long storeId;

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
	 * 审核单ID
	 */
	@Schema(description = "审核单ID")
	private Long auditId;

	/**
	 * 审核状态,0:未审核1 审核通过2审核失败3禁用中
	 */
	@Schema(description = "审核状态,0:未审核1 审核通过2审核失败3禁用中")
	private Integer auditStatus;

	/**
	 * 审核原因
	 */
	@Schema(description = "审核原因")
	private String auditReason;

	/**
	 * 搜索条件:删除时间开始
	 */
	@Schema(description = "搜索条件:删除时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeBegin;
	/**
	 * 搜索条件:删除时间截止
	 */
	@Schema(description = "搜索条件:删除时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeEnd;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

	/**
	 * 删除标记 0:未删除1:已删除
	 */
	@Schema(description = "删除标记 0:未删除1:已删除")
	private DeleteFlag delFlag;

	/**
	 * 所属店铺名称
	 */
	@Schema(description = "所属店铺名称")
	private String storeName;

	/**
	 * 最小市场价
	 */
	@Schema(description = "最小市场价")
	private BigDecimal minMarketPrice;

	/**
	 * 最大市场价
	 */
	@Schema(description = "最大市场价")
	private BigDecimal maxMarketPrice;

	/**
	 * 最小实际销量
	 */
	@Schema(description = "实际销量查询区间：最小实际销量")
	private Long minGoodsSalesNum;

	/**
	 * 最大实际销量
	 */
	@Schema(description = "实际销量查询区间：最大实际销量")
	private Long maxGoodsSalesNum;

	/**
	 * 最小库存
	 */
	@Schema(description = "库存查询区间：最小库存")
	private Long minStock;

	/**
	 * 最大库存
	 */
	@Schema(description = "库存查询区间：最大库存")
	private Long maxStock;

	/**
	 * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
	 */
	@Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
	private Integer goodsType;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Collection<Long> storeIdList;

}