package com.wanmi.sbc.message.api.request.minimsgactivitysetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Objects;

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
public class MiniMsgActivitySettingModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	@Length(max=20)
	@NotBlank
	private String activityName;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@NotNull
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@NotNull
	private LocalDateTime endTime;

	/**
	 * 活动内容
	 */
	@Schema(description = "活动内容")
	@Length(max=20)
	@NotBlank
	private String context;

	/**
	 * 温馨提示
	 */
	@Schema(description = "温馨提示")
	@Length(max=20)
	@NotBlank
	private String tips;

	/**
	 * 要跳转的页面
	 */
	@Schema(description = "要跳转的页面")
	@NotBlank
	private String toPage;

	/**
	 * 推送类型 0 立即发送  1 定时发送
	 */
	@Schema(description = "推送类型 0 立即发送  1 定时发送")
	@NotNull
	@Max(127)
	private Integer type;

	/**
	 * 定时发送时间
	 */
	@Schema(description = "定时发送时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

	/**
	 * 预计推送人数
	 */
	@Schema(description = "预计推送人数")
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
	private ProgramSendStatus sendStatus;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	@Override
	public void checkParam() {
		if (LocalDateTime.now().isAfter(startTime)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		if (endTime.isBefore(startTime)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		if (Objects.nonNull(sendTime) && LocalDateTime.now().isAfter(sendTime)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		// 定时发送，发送时间必填
		if(type== Constants.ONE&&Objects.isNull(sendTime)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}

}
