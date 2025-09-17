package com.wanmi.sbc.message.api.request.minimsgsetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.dto.MiniMsgSettingDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>小程序订阅消息配置表修改参数</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgSettingModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 小程序订阅消息配置
	 */
	@NotEmpty
	@Schema(description = "小程序订阅消息配置")
	List<MiniMsgSettingDTO> miniMsgSettingDTOList;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

}
