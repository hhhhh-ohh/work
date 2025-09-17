package com.wanmi.sbc.setting.api.request.storemessagenodesetting;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家消息节点列表查询请求参数</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageNodeSettingListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id")
	private Long storeId;

	/**
	 * 消息节点id
	 */
	@Schema(description = "消息节点id")
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
	private BoolFlag status;

	/**
	 * 删除标志 0:未删除 1:删除
	 */
	@Schema(description = "删除标志 0:未删除 1:删除")
	private DeleteFlag delFlag;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson")
	private String createPerson;

}