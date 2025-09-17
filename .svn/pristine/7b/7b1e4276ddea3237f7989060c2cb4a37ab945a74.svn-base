package com.wanmi.sbc.customer.api.request.payingmembercustomerrel;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * <p>单个查询客户与付费会员等级关联表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberCustomerRelByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Long id;

}