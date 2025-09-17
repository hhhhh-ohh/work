package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>抢购商品表列表查询请求参数</p>
 * @author bob
 * @date 2019-06-11 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	@Schema(description = "批量查询-goodsinfoIds")
	private List<String> goodsinfoIds;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 活动日期：2019-06-11
	 */
	@Schema(description = "活动日期：2019-06-11")
	private String activityDate;

	/**
	 * 活动时间：13:00
	 */
	@Schema(description = "活动时间：13:00")
	private String activityTime;

	/**
	 * skuID
	 */
	@Schema(description = "skuID")
	private String goodsInfoId;

	/**
	 * spuID
	 */
	@Schema(description = "spuID")
	private String goodsId;

	/**
	 * 抢购价
	 */
	@Schema(description = "抢购价")
	private BigDecimal price;

	/**
	 * 抢购库存
	 */
	@Schema(description = "抢购库存")
	private Integer stock;

	/**
	 * 抢购销量
	 */
	@Schema(description = "抢购销量")
	private Long salesVolume;

	/**
	 * 分类ID
	 */
	@Schema(description = "分类ID")
	private Long cateId;

	/**
	 * 限购数量
	 */
	@Schema(description = "限购数量")
	private Integer maxNum;

	/**
	 * 起售数量
	 */
	@Schema(description = "起售数量")
	private Integer minNum;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	private Long storeId;

	/**
	 * 包邮标志，0：不包邮 1:包邮
	 */
	@Schema(description = "包邮标志，0：不包邮 1:包邮")
	private Integer postage;

	/**
	 * 删除标志，0:未删除 1:已删除
	 */
	@Schema(description = "删除标志，0:未删除 1:已删除")
	private DeleteFlag delFlag;

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
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

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
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

	/**
	 * 查询时间段 0：未开始 1：正在进行 2：已结束 3：未开始与正在进行
	 */
	@Schema(description = "查询时间段，0：未开始 1：正在进行 2：已结束 3：未开始与正在进行")
	private Integer queryDataType;

	/**
	 * 类型 1:限时购 0:秒杀
	 */
	@Schema(description = "类型 1:限时购 0:秒杀")
	private Integer type;

	/**
	 * 查询所有限时抢购/秒杀
	 */
	@Schema(description = "查询所有限时抢购/秒杀")
	private Boolean findAll = false;
}