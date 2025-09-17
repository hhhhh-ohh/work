package com.wanmi.sbc.marketing.api.request.communityactivity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>社区团购活动表分页查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityActivityPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<String> activityIdList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String activityId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 批量-店铺id
	 */
	@Schema(description = "批量-店铺id")
	private List<Long> storeIds;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	private String activityName;

	/**
	 * 活动描述
	 */
	@Schema(description = "活动描述")
	private String description;

	/**
	 * 搜索条件:开始时间开始
	 */
	@Schema(description = "搜索条件:开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeBegin;
	/**
	 * 搜索条件:开始时间截止
	 */
	@Schema(description = "搜索条件:开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeEnd;

	/**
	 * 搜索条件:结束时间开始
	 */
	@Schema(description = "搜索条件:结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeBegin;
	/**
	 * 搜索条件:结束时间截止
	 */
	@Schema(description = "搜索条件:结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeEnd;

	/**
	 * 搜索条件:延时结束时间截止
	 */
	@Schema(description = "搜索条件:延时结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime realEndTimeEnd;

	/**
	 * 物流方式 0:自提 1:快递 以逗号拼凑
	 */
	@Schema(description = "物流方式 0:自提 1:快递")
	private List<CommunityLogisticsType> logisticsTypes;

	/**
	 * 销售渠道 0:自主销售 1:团长帮卖 以逗号拼凑
	 */
	@Schema(description = "销售渠道 0:自主销售 1:团长帮卖")
	private List<CommunitySalesType> salesTypes;

	/**
	 * 自主销售范围 0：全部 1：地区 2：自定义
	 */
	@Schema(description = "自主销售范围 0：全部 1：地区 2：自定义")
	private CommunitySalesRangeType salesRange;

	/**
	 * 帮卖团长范围 0：全部 1：地区 2：自定义
	 */
	@Schema(description = "帮卖团长范围 0：全部 1：地区 2：自定义")
	private CommunityLeaderRangeType leaderRange;

	/**
	 * 佣金设置 0：商品 1：按团长/自提点
	 */
	@Schema(description = "佣金设置 0：商品 1：按团长/自提点")
	private CommunityCommissionFlag commissionFlag;

	/**
	 * 批量-自提服务佣金
	 */
	@Schema(description = "批量-自提服务佣金")
	private BigDecimal pickupCommission;

	/**
	 * 批量-帮卖团长佣金
	 */
	@Schema(description = "批量-帮卖团长佣金")
	private BigDecimal assistCommission;

	/**
	 * 团详情
	 */
	@Schema(description = "团详情")
	private String details;

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
	 * 搜索条件:结束时间开始
	 */
	@Schema(description = "搜索条件:结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:结束时间截止
	 */
	@Schema(description = "搜索条件:结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 是否生成 0:未生成 1:已生成
	 */
	@Schema(description = "是否生成 0:未生成 1:已生成")
	private Integer generateFlag;


	/**
	 * 是否查询销售范围
	 */
	@Schema(description = "是否查询销售范围 true:查询")
	private Boolean saleRelFlag;

	/**
	 * 是否查询佣金设置
	 */
	@Schema(description = "是否查询佣金设置 true:查询")
	private Boolean commissionRelFlag;

	/**
	 * 是否查询商品关联
	 */
	@Schema(description = "是否查询商品关联 true:商品关联")
	private Boolean skuRelFlag;

	/**
	 * 活动时间状态
	 */
	@Schema(description = "活动时间状态 0:进行中 1:已结束 2:未开始")
	private CommunityTabStatus tabType;

	/**
	 * 搜索条件:结束时间开始
	 */
	@Schema(description = "搜索条件:结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;
	/**
	 * 搜索条件:结束时间截止
	 */
	@Schema(description = "搜索条件:结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 帮卖团长搜索条件:是否帮卖团长
	 */
	@Schema(description = "帮卖团长搜索条件:是否帮卖团长")
	private Boolean assistLeaderFlag;

	/**
	 * 帮卖团长搜索条件:帮卖自提点id
	 */
	@Schema(description = "帮卖团长搜索条件:帮卖自提点id")
	private String assistPickupPointId;

	/**
	 * 帮卖团长搜索条件:帮卖团长id
	 */
	@Schema(description = "帮卖团长搜索条件:帮卖团长的区域")
	private List<String> assistAreaIds;
}