package com.wanmi.sbc.account.bean.vo;

import java.math.BigDecimal;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>会员分账资金VO</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@Schema
@Data
public class LedgerFundsVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

	/**
	 * 待提现金额
	 */
	@Schema(description = "待提现金额")
	private BigDecimal withdrawnAmount;

	/**
	 * 已提现金额
	 */
	@Schema(description = "已提现金额")
	private BigDecimal alreadyDrawAmount;

}