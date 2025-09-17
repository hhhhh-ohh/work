package com.wanmi.sbc.message.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>小程序订阅消息配置表VO</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@Schema
@Data
public class MiniMsgAuthorizationRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	private String customerId;

	/**
	 * 订单支付成功次数
	 */
	@Schema(description = "订单支付成功次数")
	private Integer orderPaySuccessNum;

	/**
	 * 退单提交成功次数
	 */
	@Schema(description = "退单提交成功次数")
	private Integer refundOrderSuccessNum;

	/**
	 * 查看我的优惠券次数
	 */
	@Schema(description = "查看我的优惠券次数")
	private Integer viewCouponNum;

	/**
	 * 参与/发起拼团成功次数
	 */
	@Schema(description = "参与/发起拼团成功次数")
	private Integer grouponSuccessNum;

	/**
	 * 商品预约成功次数
	 */
	@Schema(description = "商品预约成功次数")
	private Integer appointmentSuccessNum;

	/**
	 * 付费会员购买成功次数
	 */
	@Schema(description = "付费会员购买成功次数")
	private Integer memberPaySuccessNum;

}