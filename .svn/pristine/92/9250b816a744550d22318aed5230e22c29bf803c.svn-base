package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import java.time.LocalDate;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>运营计划效果统计站内信收到人/次统计数据VO</p>
 * @author lvzhenwei
 * @date 2020-02-05 15:08:00
 */
@Schema
@Data
public class PlanStatisticsMessageVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

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
	 * 统计日期
	 */
	@Schema(description = "统计日期")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate statisticsDate;

}