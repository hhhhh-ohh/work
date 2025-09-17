package com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

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
public class WxPayUploadShippingInfoModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 支付类型，0：商品订单支付；1：授信还款
	 */
	@Schema(description = "支付类型，0：商品订单支付；1：授信还款")
	@Max(127)
	private Integer payType;

	/**
	 * 支付订单id
	 */
	@Schema(description = "支付订单id")
	@NotBlank
	@Length(max=45)
	private String businessId;

	/**
	 * 支付流水id
	 */
	@Schema(description = "支付流水id")
	@NotBlank
	@Length(max=45)
	private String transactionId;

	/**
	 * 商户号
	 */
	@Schema(description = "商户号")
	@NotBlank
	@Length(max=45)
	private String mchId;

	/**
	 * 接口调用返回结果内容
	 */
	@Schema(description = "接口调用返回结果内容")
	private String resultContext;

	/**
	 * 结果状态，0：待处理；1：处理成功；2：处理失败
	 */
	@Schema(description = "结果状态，0：待处理；1：处理成功；2：处理失败")
	@Max(127)
	private Integer resultStatus;

	/**
	 * 处理失败次数
	 */
	@Schema(description = "处理失败次数")
	@Max(127)
	private Integer errorNum;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}
