package com.wanmi.sbc.elastic.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>优惠券活动范围修改参数</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsActivityCouponModifyRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券活动id
	 */
	@Schema(description = "优惠券活动id")
	@NotNull
	private String activityId;

	/**
	 * 优惠券活动id
	 */
	@Schema(description = "优惠券活动id")
	private String couponId;

	/**
	 * 是否剩余
	 */
	@Schema(description = "是否剩余")
	private DefaultFlag hasLeft;

	/**
	 * 是否暂停 ，1 暂停
	 */
	@Schema(description = "是否暂停")
	private DefaultFlag pauseFlag;

	/**
	 * 活动结束时间
	 */
	@Schema(description = "活动结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime activityEndTime;

	/**
	 * 优惠券分类Id
	 */
	@Schema(description = "优惠券分类Id")
	private String couponCateId;

	/**
	 * true删除分类/false设置平台可用
	 */
	@Schema(description = "true删除分类/false设置平台可用")
	private boolean delete;


}
