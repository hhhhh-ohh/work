package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>运营计划转化效果VO</p>
 * @author zhangwenchang
 * @date 2020-02-12 00:16:50
 */
@Schema
@Data
public class CustomerPlanConversionVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@Schema(description = "主键ID")
	private Long id;

	/**
	 * 运营计划id
	 */
	@Schema(description = "运营计划id")
	private Long planId;

	/**
	 * 访客数UV
	 */
	@Schema(description = "访客数UV")
	private Long visitorsUvCount;

	/**
	 * 下单人数
	 */
	@Schema(description = "下单人数")
	private Long orderPersonCount;

	/**
	 * 下单笔数
	 */
	@Schema(description = "下单笔数")
	private Long orderCount;

	/**
	 * 付款人数
	 */
	@Schema(description = "付款人数")
	private Long payPersonCount;

	/**
	 * 付款笔数
	 */
	@Schema(description = "付款笔数")
	private Long payCount;

	/**
	 * 付款金额
	 */
	@Schema(description = "付款金额")
	private BigDecimal totalPrice;

	/**
	 * 客单价
	 */
	@Schema(description = "客单价")
	private BigDecimal unitPrice;

	/**
	 * 覆盖人数
	 */
	@Schema(description = "覆盖人数")
	private Long coversCount;

	/**
	 * 访客人数
	 */
	@Schema(description = "访客人数")
	private Long visitorsCount;

	/**
	 * 访客人数/覆盖人数转换率
	 */
	@Schema(description = "访客人数/覆盖人数转换率")
	private Double coversVisitorsRate;

	/**
	 * 付款人数/访客人数转换率
	 */
	@Schema(description = "付款人数/访客人数转换率")
	private Double payVisitorsRate;

	/**
	 * 付款人数/覆盖人数转换率
	 */
	@Schema(description = "付款人数/覆盖人数转换率")
	private Double payCoversRate;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}