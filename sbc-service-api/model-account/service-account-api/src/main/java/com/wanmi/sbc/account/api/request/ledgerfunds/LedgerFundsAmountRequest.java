package com.wanmi.sbc.account.api.request.ledgerfunds;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.math.BigDecimal;

/**
 * <p>会员分账资金修改参数</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFundsAmountRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	@NotBlank
	private String customerId;

	/**
	 * 金额
	 */
	@Schema(description = "金额")
	private BigDecimal amount;

}
