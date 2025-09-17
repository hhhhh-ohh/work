package com.wanmi.sbc.order.api.request.refundcallbackresult;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.RefundCallBackResultDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>支付回调结果新增参数</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundCallBackResultAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	@Schema(description = "退款回调对象")
	private RefundCallBackResultDTO refundCallBackResultDTO;
}