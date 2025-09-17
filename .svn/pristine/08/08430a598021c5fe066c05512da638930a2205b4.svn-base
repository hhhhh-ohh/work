package com.wanmi.sbc.setting.api.request.storemessagenodesetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>商家消息节点新增参数</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageNodeSettingAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id")
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 消息节点id
	 */
	@Schema(description = "消息节点id")
	@Max(9223372036854775807L)
	private Long nodeId;

	/**
	 * 消息节点标识
	 */
	@Schema(description = "消息节点标识")
	private String nodeCode;

	/**
	 * 启用状态 0:未启用 1:启用
	 */
	@Schema(description = "启用状态 0:未启用 1:启用")
	@Max(127)
	private BoolFlag status;

	/**
	 * 删除标志 0:未删除 1:删除
	 */
	@Schema(description = "删除标志 0:未删除 1:删除", hidden = true)
	private DeleteFlag delFlag;

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

}