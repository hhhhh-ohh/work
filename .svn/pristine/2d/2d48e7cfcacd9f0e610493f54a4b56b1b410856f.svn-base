package com.wanmi.sbc.message.api.request.minimsgactivitysetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>小程序订阅消息配置表修改参数</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgActivitySettingModifyByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 实际推送人数
	 */
	@Schema(description = "实际推送人数")
	@Max(9999999999L)
	private Integer preCount;

	/**
	 * 实际推送人数
	 */
	@Schema(description = "实际推送人数")
	@Max(9999999999L)
	private Integer realCount;

	/**
	 * 推送状态 0：未推送，1：推送中，2：已推送，3：推送失败，4：部分失败
	 */
	@Schema(description = "推送状态 0：未推送，1：推送中，2：已推送，3：推送失败，4：部分失败")
	@NotNull
	private ProgramSendStatus sendStatus;

}
