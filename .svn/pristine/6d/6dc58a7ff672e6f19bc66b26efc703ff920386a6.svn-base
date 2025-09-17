package com.wanmi.sbc.crm.api.request.customerplansendcount;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>权益礼包优惠券发放统计表列表查询请求参数</p>
 * @author zhanghao
 * @date 2020-02-04 13:29:18
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanSendCountListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-礼包优惠券发放统计idList
	 */
	@Schema(description = "批量查询-礼包优惠券发放统计idList")
	private List<Long> idList;

	/**
	 * 礼包优惠券发放统计id
	 */
	@Schema(description = "礼包优惠券发放统计id")
	private Long id;

	/**
	 * 运营计划id
	 */
	@Schema(description = "运营计划id")
	private Long planId;

	/**
	 * 发放礼包人数
	 */
	@Schema(description = "发放礼包人数")
	private Long giftPersonCount;

	/**
	 * 发放礼包次数
	 */
	@Schema(description = "发放礼包次数")
	private Long giftCount;

	/**
	 * 发放优惠券人数
	 */
	@Schema(description = "发放优惠券人数")
	private Long couponPersonCount;

	/**
	 * 发放优惠券张数
	 */
	@Schema(description = "发放优惠券张数")
	private Long couponCount;

	/**
	 * 优惠券使用人数
	 */
	@Schema(description = "优惠券使用人数")
	private Long couponPersonUseCount;

	/**
	 * 优惠券使用张数
	 */
	@Schema(description = "优惠券使用张数")
	private Long couponUseCount;

	/**
	 * 优惠券使用率
	 */
	@Schema(description = "优惠券使用率")
	private Double couponUseRate;

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

}