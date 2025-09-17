package com.wanmi.sbc.setting.api.request.push;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>消息推送新增参数</p>
 * @author chenyufei
 * @date 2019-05-10 14:39:59
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushConfigAddRequest extends SettingBaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 消息推送配置名称
	 */
	@Schema(description = "消息推送配置名称")
	@NotBlank
	@Length(max=255)
	private String appPushName;

	/**
	 * 消息推送提供商  0:友盟
	 */
	@Schema(description = "消息推送提供商  0:友盟")
	@NotBlank
	@Length(max=2)
	private String appPushManufacturer;

	/**
	 * Android App Key
	 */
	@Schema(description = "Android App Key")
	@Length(max=255)
	private String androidAppKey;

	/**
	 * Android Umeng Message Secret
	 */
	@Schema(description = "Android Umeng Message Secret")
	@Length(max=255)
	private String androidUmengMessageSecret;

	/**
	 * Android App Master Secret
	 */
	@Schema(description = "Android App Master Secret")
	@Length(max=255)
	private String androidAppMasterSecret;

	/**
	 * IOS App Key
	 */
	@Schema(description = "IOS App Key")
	@Length(max=255)
	private String iosAppKey;

	/**
	 * IOS App Master Secret
	 */
	@Schema(description = "IOS App Master Secret")
	@Length(max=255)
	private String iosAppMasterSecret;

	/**
	 * 状态,0:未启用1:已启用
	 */
	@Schema(description = "状态,0:未启用1:已启用")
	private Integer status;

	/**
	 * 创建日期
	 */
	@Schema(description = "创建日期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新日期
	 */
	@Schema(description = "更新日期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除日期
	 */
	@Schema(description = "删除日期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime delTime;

	/**
	 * 删除标志
	 */
	@Schema(description = "删除标志")
	private DeleteFlag delFlag;

}