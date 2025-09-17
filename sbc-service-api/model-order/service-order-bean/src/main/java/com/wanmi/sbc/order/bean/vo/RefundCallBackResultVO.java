package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayCallBackType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>退款回调结果VO</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@Schema
@Data
public class RefundCallBackResultVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String businessId;

	/**
	 * 回调结果xml内容
	 */
	@Schema(description = "回调结果xml内容")
	private String resultXml;

	/**
	 * 回调结果内容
	 */
	@Schema(description = "回调结果内容")
	private String resultContext;

	/**
	 * 结果状态，0：待处理；1:处理中 2：处理成功；3：处理失败
	 */
	@Schema(description = "结果状态，0：待处理；1:处理中 2：处理成功；3：处理失败")
	private PayCallBackResultStatus resultStatus;

	/**
	 * 处理失败次数
	 */
	@Schema(description = "处理失败次数")
	private Integer errorNum;

	/**
	 * 支付方式，0：微信；1：支付宝；2：银联
	 */
	@Schema(description = "支付方式，0：微信；1：支付宝；2：银联")
	private PayCallBackType payType;

}