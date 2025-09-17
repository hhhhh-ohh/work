package com.wanmi.sbc.customer.api.request.payingmemberlevel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询付费会员等级表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberLevelByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	@NotNull
	private Integer levelId;

	/**
	 * 是否是客户查询
	 */
	@Schema(description = "是否是客户查询")
	private boolean isCustomer;

}