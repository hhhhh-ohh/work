package com.wanmi.sbc.order.api.request.paycallbackresult;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayCallBackType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.Length;

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
public class PayCallBackResultAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	@NotBlank
	@Length(max=45)
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
	@Max(127)
	private Integer errorNum;

	/**
	 * 支付方式，0：微信；1：支付宝；2：银联
	 */
	@Schema(description = "支付方式，0：微信；1：支付宝；2：银联")
	private PayCallBackType payType;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}