package com.wanmi.sbc.crm.api.request.customerplansendcount;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除权益礼包优惠券发放统计表请求参数</p>
 * @author zhanghao
 * @date 2020-02-04 13:29:18
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanSendCountDelByIdRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 礼包优惠券发放统计id
	 */
	@Schema(description = "礼包优惠券发放统计id")
	@NotNull
	private Long id;
}