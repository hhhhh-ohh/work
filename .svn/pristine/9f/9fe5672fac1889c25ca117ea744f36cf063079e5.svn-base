package com.wanmi.sbc.empower.api.request.ledgermcc;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>拉卡拉mcc表新增参数</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerMccAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

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
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

}