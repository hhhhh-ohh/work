package com.wanmi.sbc.marketing.api.request.communityrel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购活动区域关联表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:38:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityAreaRelQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	private String activityId;

	/**
	 * 批量-活动id
	 */
	@Schema(description = "批量-活动id")
	private List<String> activityIdList;

	/**
	 * 区域Id
	 */
	@Schema(description = "区域Id")
	private String areaId;

	/**
	 * 批量-区域Id
	 */
	@Schema(description = "批量-区域Id")
	private List<String> areaIdList;

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
	 * 销售渠道 0:自主销售 1:团长帮卖
	 */
	@Schema(description = "销售渠道 0:自主销售 1:团长帮卖")
	private CommunitySalesType salesType;

	/**
	 * 活动时间状态
	 */
	@Schema(description = "活动时间状态 0:进行中 1:已结束 2:未开始")
	private CommunityTabStatus tabType;
}