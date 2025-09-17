package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>授信订单信息VO</p>
 * @author chenli
 * @date 2021-03-04 14:03:47
 */
@Schema
@Data
public class CustomerCreditOrderVO extends BasicResponse {

	private static final long serialVersionUID = -4916698135376245422L;
	/**
	 *  主键
	 */
	@Schema(description = " 主键")
	private String id;

	/**
	 * 还款单号
	 */
	@Schema(description = "还款单号")
	private String repayOrderCode;

	/**
	 * 关联订单id
	 */
	@Schema(description = "关联订单id")
	private String orderId;

	/**
	 * 客户id
	 */
	@Schema(description = "客户id")
	private String customerId;
}