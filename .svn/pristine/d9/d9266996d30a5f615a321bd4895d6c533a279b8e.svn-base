package com.wanmi.sbc.customer.api.request.ledgersupplier;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>商户分账绑定数据新增参数</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerSupplierAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 清分账户id
	 */
	@Schema(description = "清分账户id")
	@Length(max=32)
	private String ledgerAccountId;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id")
	@Max(9223372036854775807L)
	private Long companyInfoId;

	/**
	 * 商家名称
	 */
	@Schema(description = "商家名称")
	@Length(max=50)
	private String companyName;

	/**
	 * 商家编号
	 */
	@Schema(description = "商家编号")
	@Length(max=32)
	private String companyCode;

	/**
	 * 平台绑定状态 0、未绑定 1、已绑定
	 */
	@Schema(description = "平台绑定状态 0、未绑定 1、已绑定")
	@Max(127)
	private Integer platBindState;

	/**
	 * 供应商绑定数
	 */
	@Schema(description = "供应商绑定数")
	@Max(9223372036854775807L)
	private Long providerNum;

	/**
	 * 分销员绑定数
	 */
	@Schema(description = "分销员绑定数")
	@Max(9223372036854775807L)
	private Long distributionNum;

}