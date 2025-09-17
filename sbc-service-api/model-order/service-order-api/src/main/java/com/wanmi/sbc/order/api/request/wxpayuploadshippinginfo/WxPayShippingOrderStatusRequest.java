package com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * <p>微信小程序支付发货信息修改参数</p>
 * @author 吕振伟
 * @date 2023-07-24 14:13:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxPayShippingOrderStatusRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单
	 */
	@NotBlank
	@Schema(description = "订单号")
	private String tradeId;
}
