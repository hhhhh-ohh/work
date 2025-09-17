package com.wanmi.sbc.order.bean.vo;

import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>微信小程序支付发货信息VO</p>
 * @author 吕振伟
 * @date 2023-07-24 14:13:21
 */
@Schema
@Data
public class WxPayUploadShippingInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 支付类型，0：商品订单支付；1：授信还款
	 */
	@Schema(description = "支付类型，0：商品订单支付；1：授信还款")
	private Integer payType;

	/**
	 * 支付订单id
	 */
	@Schema(description = "支付订单id")
	private String businessId;

	/**
	 * 支付流水id
	 */
	@Schema(description = "支付流水id")
	private String transactionId;

	/**
	 * 商户号
	 */
	@Schema(description = "商户号")
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
	private Integer resultStatus;

	/**
	 * 处理失败次数
	 */
	@Schema(description = "处理失败次数")
	private Integer errorNum;

}