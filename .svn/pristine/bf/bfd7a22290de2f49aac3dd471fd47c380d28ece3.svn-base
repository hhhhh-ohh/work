package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.RuleType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>规则说明VO</p>
 * @author zxd
 * @date 2020-05-25 18:55:56
 */
@Schema
@Data
public class RuleInfoVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 规则名称
	 */
	@Schema(description = "规则名称")
	private String ruleName;

	/**
	 * 规则说明
	 */
	@Schema(description = "规则说明")
	private String ruleContent;

	/**
	 * 规则类型 0:预约 1:预售
	 */
	@Schema(description = "规则类型 0:预约 1:预售")
	private RuleType ruleType;

}