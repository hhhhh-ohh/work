package com.wanmi.sbc.crm.api.request.planstatisticsmessage;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import java.time.LocalDate;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>运营计划效果统计站内信收到人/次统计数据通用查询请求参数</p>
 * @author lvzhenwei
 * @date 2020-02-05 15:08:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanStatisticsMessageQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-运营计划idList
	 */
	@Schema(description = "批量查询-运营计划idList")
	private List<Long> planIdList;

	/**
	 * 运营计划id
	 */
	@Schema(description = "运营计划id")
	private Long planId;

	/**
	 * 站内信收到人数
	 */
	@Schema(description = "站内信收到人数")
	private Integer messageReceiveNum;

	/**
	 * 站内信收到人次
	 */
	@Schema(description = "站内信收到人次")
	private Integer messageReceiveTotal;

	/**
	 * 搜索条件:统计日期开始
	 */
	@Schema(description = "搜索条件:统计日期开始")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate statisticsDateBegin;
	/**
	 * 搜索条件:统计日期截止
	 */
	@Schema(description = "搜索条件:统计日期截止")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate statisticsDateEnd;

}