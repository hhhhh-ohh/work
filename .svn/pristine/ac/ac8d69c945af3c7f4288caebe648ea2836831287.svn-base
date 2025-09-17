package com.wanmi.sbc.empower.api.request.sms;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import com.wanmi.sbc.empower.bean.enums.SmsPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>短信配置新增参数</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:15:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSettingAddRequest extends EmpowerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 调用api参数key
	 */
	@Schema(description = "调用api参数key")
	private String accessKeyId;

	/**
	 * 调用api参数secret
	 */
	@Schema(description = "调用api参数secret")
	private String accessKeySecret;

	/**
	 * 短信平台类型：0：阿里云短信平台
	 */
	@Schema(description = "短信平台类型：0：阿里云短信平台")
	private SmsPlatformType type;

	/**
	 * 是否启用：0：未启用；1：启用
	 */
	@Schema(description = "是否启用：0：未启用；1：启用")
	private EnableStatus status;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除")
	private DeleteFlag delFlag;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}