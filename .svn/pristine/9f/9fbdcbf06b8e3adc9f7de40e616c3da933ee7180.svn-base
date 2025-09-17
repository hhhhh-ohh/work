package com.wanmi.sbc.empower.deliveryrecord.request;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>达达配送订单请求</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DadaCancelRequest extends DadaBaseRequest {

	/**
	 * 订单号
	 */
    @JSONField(name = "order_id")
	private String orderNo;

	/**
	 * 取消理由id
	 */
	@JSONField(name = "cancel_reason_id")
	private Integer cancelReasonId;

	/**
	 * 当取消理由为其他时，此项必填
	 */
	@JSONField(name = "cancel_reason")
	private String cancelReason;
}