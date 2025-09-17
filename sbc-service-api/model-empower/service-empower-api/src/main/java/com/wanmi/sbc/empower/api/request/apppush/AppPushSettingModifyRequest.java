package com.wanmi.sbc.empower.api.request.apppush;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import com.wanmi.sbc.empower.bean.enums.AppPushPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>消息推送配置修改参数</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushSettingModifyRequest extends EmpowerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9999999999L)
	private Integer id;

	/**
	 * 推送平台类型：0：友盟
	 */
	@Schema(description = "推送平台类型：0：友盟")
	@NotNull
	private AppPushPlatformType platformType;

	/**
	 * 安卓应用的Appkey
	 */
	@Schema(description = "安卓应用的Appkey")
	@Length(max=64)
	private String androidKeyId;

	/**
	 * 安卓应用的Message Secret
	 */
	@Schema(description = "安卓应用的Message Secret")
	@Length(max=64)
	private String androidMsgSecret;

	/**
	 * 安卓应用的服务器秘钥
	 */
	@Schema(description = "安卓应用的服务器秘钥")
	@Length(max=64)
	private String androidKeySecret;

	/**
	 * IOS应用的Appkey
	 */
	@Schema(description = "IOS应用的Appkey")
	@Length(max=64)
	private String iosKeyId;

	/**
	 * IOS应用的服务器秘钥
	 */
	@Schema(description = "IOS应用的服务器秘钥")
	@Length(max=64)
	private String iosKeySecret;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}