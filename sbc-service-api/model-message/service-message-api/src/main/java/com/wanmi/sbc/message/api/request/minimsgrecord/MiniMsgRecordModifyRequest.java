package com.wanmi.sbc.message.api.request.minimsgrecord;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ProgramNodeType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

/**
 * <p>小程序订阅消息配置表修改参数</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgRecordModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	@Length(max=32)
	private String customerId;

	/**
	 * 订单支付成功次数
	 */
	@Schema(description = "订单支付成功次数")
	@Max(127)
	private Integer orderPaySuccessNum;

	/**
	 * 退单提交成功次数
	 */
	@Schema(description = "退单提交成功次数")
	@Max(127)
	private Integer refundOrderSuccessNum;

	/**
	 * 查看我的优惠券次数
	 */
	@Schema(description = "查看我的优惠券次数")
	@Max(127)
	private Integer viewCouponNum;

	/**
	 * 参与/发起拼团成功次数
	 */
	@Schema(description = "参与/发起拼团成功次数")
	@Max(127)
	private Integer grouponSuccessNum;

	/**
	 * 商品预约成功次数
	 */
	@Schema(description = "商品预约成功次数")
	@Max(127)
	private Integer appointmentSuccessNum;

	/**
	 * 付费会员购买成功次数
	 */
	@Schema(description = "付费会员购买成功次数")
	@Max(127)
	private Integer memberPaySuccessNum;

	/**
	 * 授权节点id 0 订单支付成功 1 退单提交成功 2 查看我的优惠券 3 参与/发起拼团成功 4 商品预约成功 5 付费会员购买成功
	 */
	@Schema(description = "授权节点id 0 订单支付成功 1 退单提交成功 2 查看我的优惠券 3 参与/发起拼团成功 4 商品预约成功 5 付费会员购买成功")
	private ProgramNodeType nodeId;

}
