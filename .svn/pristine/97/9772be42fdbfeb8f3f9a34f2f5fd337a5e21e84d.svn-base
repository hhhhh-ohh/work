package com.wanmi.sbc.message.api.request.pushcustomerenable;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

/**
 * <p>会员推送通知开关新增参数</p>
 * @author Bob
 * @date 2020-01-07 15:31:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushCustomerEnableAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 开启状态 0:未开启 1:启用
	 */
	@Schema(description = "开启状态 0:未开启 1:启用")
	@Max(127)
	private Integer enableStatus;

	/**
	 * 账号安全通知 0:未启用 1:启用
	 */
	@Schema(description = "账号安全通知 0:未启用 1:启用")
	@Max(127)
	private Integer accountSecurity;

	/**
	 * 账户资产通知 0:未启用 1:启用
	 */
	@Schema(description = "账户资产通知 0:未启用 1:启用")
	@Max(127)
	private Integer accountAssets;

	/**
	 * 订单进度通知 0:未启用 1:启用
	 */
	@Schema(description = "订单进度通知 0:未启用 1:启用")
	@Max(127)
	private Integer orderProgressRate;

	/**
	 * 退单进度通知 0:未启用 1:启用
	 */
	@Schema(description = "退单进度通知 0:未启用 1:启用")
	@Max(127)
	private Integer returnOrderProgressRate;

	/**
	 * 分销业务通知 0:未启用 1:启用
	 */
	@Schema(description = "分销业务通知 0:未启用 1:启用")
	@Max(127)
	private Integer distribution;

	/**
	 * 删除标志 0:未删除 1:删除
	 */
	@Schema(description = "删除标志 0:未删除 1:删除", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人ID
	 */
	@Schema(description = "创建人ID", hidden = true)
	private String createPerson;

	/**
	 * 更新人ID
	 */
	@Schema(description = "更新人ID", hidden = true)
	private String updatePerson;

}