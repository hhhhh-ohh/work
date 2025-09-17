package com.wanmi.sbc.goods.api.request.ruleinfo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.RuleType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>规则说明新增参数</p>
 * @author zxd
 * @date 2020-05-25 18:55:56
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfoAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 规则名称
	 */
	@Schema(description = "规则名称")
	@Length(max=128)
	private String ruleName;

	/**
	 * 规则说明
	 */
	@Schema(description = "规则说明")
	@Length(max=500)
	private String ruleContent;

	/**
	 * 规则类型 0:预约 1:预售
	 */
	@Schema(description = "规则类型 0:预约 1:预售")
	@NotNull
	private RuleType ruleType;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}