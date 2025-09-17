package com.wanmi.sbc.setting.api.request.storemessagenode;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.storemessage.StoreMessagePlatform;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>商家消息节点新增参数</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageNodeAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属菜单名称
	 */
	@Schema(description = "所属菜单名称")
	@Length(max=32)
	private String menuName;

	/**
	 * 节点类型名称
	 */
	@Schema(description = "节点类型名称")
	@Length(max=32)
	private String typeName;

	/**
	 * 推送节点名称
	 */
	@Schema(description = "推送节点名称")
	@Length(max=32)
	private String pushName;

	/**
	 * 功能标识，用于鉴权
	 */
	@Schema(description = "功能标识，用于鉴权")
	@Length(max=100)
	private String functionName;

	/**
	 * 节点标识
	 */
	@Schema(description = "节点标识")
	@Length(max=50)
	private String nodeCode;

	/**
	 * 节点通知内容模板
	 */
	@Schema(description = "节点通知内容模板")
	private String nodeContext;

	/**
	 * 平台类型 0:平台 1:商家 2:供应商
	 */
	@Schema(description = "平台类型 0:平台 1:商家 2:供应商")
	@Max(127)
	private StoreMessagePlatform platformType;

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