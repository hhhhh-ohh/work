package com.wanmi.sbc.crm.api.request.customerplansendcount;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>权益礼包优惠券发放统计表新增参数</p>
 * @author zhanghao
 * @date 2020-02-04 13:29:18
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanSendCountAddRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 运营计划id
	 */
	@Schema(description = "运营计划id")
	@Max(9223372036854775807L)
	private Long planId;

	/**
	 * 发放礼包人数
	 */
	@Schema(description = "发放礼包人数")
	@Max(9223372036854775807L)
	private Long giftPersonCount;

	/**
	 * 发放礼包次数
	 */
	@Schema(description = "发放礼包次数")
	@Max(9223372036854775807L)
	private Long giftCount;

	/**
	 * 发放优惠券人数
	 */
	@Schema(description = "发放优惠券人数")
	@Max(9223372036854775807L)
	private Long couponPersonCount;

	/**
	 * 发放优惠券张数
	 */
	@Schema(description = "发放优惠券张数")
	@Max(9223372036854775807L)
	private Long couponCount;

	/**
	 * 优惠券使用人数
	 */
	@Schema(description = "优惠券使用人数")
	@Max(9223372036854775807L)
	private Long couponPersonUseCount;

	/**
	 * 优惠券使用张数
	 */
	@Schema(description = "优惠券使用张数")
	@Max(9223372036854775807L)
	private Long couponUseCount;

	/**
	 * 优惠券使用率
	 */
	@Schema(description = "优惠券使用率")
	private Double couponUseRate;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}