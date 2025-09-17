package com.wanmi.sbc.empower.api.request.ledgermcc;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>拉卡拉mcc表修改参数</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerMccModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * mcc编号
	 */
	@Schema(description = "mcc编号")
	@Max(9223372036854775807L)
	private Long mccId;

	/**
	 * mcc类别
	 */
	@Schema(description = "mcc类别")
	@Length(max=128)
	private String mccCate;

	/**
	 * 商户类别名
	 */
	@Schema(description = "商户类别名")
	@Length(max=128)
	private String supplierCateName;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

}
