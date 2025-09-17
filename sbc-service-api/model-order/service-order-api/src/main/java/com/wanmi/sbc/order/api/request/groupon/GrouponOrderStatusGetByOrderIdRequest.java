package com.wanmi.sbc.order.api.request.groupon;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>团订单状态</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponOrderStatusGetByOrderIdRequest extends BaseRequest {
	private static final long serialVersionUID = -4493594277885985685L;


	/**
	 * 订单ID
	 */
	@Schema(description = "订单ID")
	private String orderId;

}