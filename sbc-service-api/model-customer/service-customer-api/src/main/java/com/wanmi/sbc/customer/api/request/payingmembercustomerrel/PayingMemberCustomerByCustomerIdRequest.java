package com.wanmi.sbc.customer.api.request.payingmembercustomerrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>个人中心-付费会员入口-查询参数</p>
 * @author chenli
 * @date 2022-05-20 13:40:48
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberCustomerByCustomerIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

}