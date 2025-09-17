package com.wanmi.sbc.crm.api.request.customerplanconversion;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>运营计划转化效果修改参数</p>
 * @author zhangwenchang
 * @date 2020-02-12 00:16:50
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanConversionModifyRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@Schema(description = "主键ID")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 运营计划id
	 */
	@Schema(description = "运营计划id")
	@Max(9223372036854775807L)
	private Long planId;

	/**
	 * 访客数UV
	 */
	@Schema(description = "访客数UV")
	@Max(9223372036854775807L)
	private Long visitorsUvCount;

	/**
	 * 下单人数
	 */
	@Schema(description = "下单人数")
	@Max(9223372036854775807L)
	private Long orderPersonCount;

	/**
	 * 下单笔数
	 */
	@Schema(description = "下单笔数")
	@Max(9223372036854775807L)
	private Long orderCount;

	/**
	 * 付款人数
	 */
	@Schema(description = "付款人数")
	@Max(9223372036854775807L)
	private Long payPersonCount;

	/**
	 * 付款笔数
	 */
	@Schema(description = "付款笔数")
	@Max(9223372036854775807L)
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
	@Max(9223372036854775807L)
	private Long coversCount;

	/**
	 * 访客人数
	 */
	@Schema(description = "访客人数")
	@Max(9223372036854775807L)
	private Long visitorsCount;

	/**
	 * 访客人数/覆盖人数转换率
	 */
	@Schema(description = "访客人数/覆盖人数转换率")
	private BigDecimal coversVisitorsRate;

	/**
	 * 付款人数/访客人数转换率
	 */
	@Schema(description = "付款人数/访客人数转换率")
	private BigDecimal payVisitorsRate;

	/**
	 * 付款人数/覆盖人数转换率
	 */
	@Schema(description = "付款人数/覆盖人数转换率")
	private BigDecimal payCoversRate;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}