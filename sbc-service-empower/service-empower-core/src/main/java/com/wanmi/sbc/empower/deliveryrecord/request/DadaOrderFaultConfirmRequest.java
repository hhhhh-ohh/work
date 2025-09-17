package com.wanmi.sbc.empower.deliveryrecord.request;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>达达配送妥投异常确认请求</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DadaOrderFaultConfirmRequest extends DadaBaseRequest {

	/**
	 * 订单号
	 */
    @JSONField(name = "order_id")
	private String orderNo;
}