package com.wanmi.sbc.empower.api.request.ledgercontent;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>拉卡拉经营内容表新增参数</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContentAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 经营内容名称
	 */
	@Schema(description = "经营内容名称")
	@Length(max=128)
	private String contentName;

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