package com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

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
public class WxPayUploadShippingInfoSyncRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单
	 */
	@Schema(description = "订单号")
	private List<String> tradeIds;
}
