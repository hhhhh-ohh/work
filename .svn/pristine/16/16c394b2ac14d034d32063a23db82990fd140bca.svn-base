package com.wanmi.sbc.setting.api.request.systemresource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>平台素材资源新增参数</p>
 * @author lq
 * @date 2019-11-05 16:14:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceAddRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 资源类型(0:图片,1:视频)
	 */
	@Schema(description = "资源类型(0:图片,1:视频)")
	@NotNull
	private ResourceType resourceType;

	/**
	 * 素材分类ID
	 */
	@Schema(description = "素材分类ID")
	@NotNull
	@Max(9223372036854775807L)
	private Long cateId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 商家标识
	 */
	@Schema(description = "商家标识")
	@Max(9999999999L)
	private Long companyInfoId;

	/**
	 * 素材KEY
	 */
	@Schema(description = "素材KEY")
	@Length(max=255)
	private String resourceKey;

	/**
	 * 素材名称
	 */
	@Schema(description = "素材名称")
	@Length(max=45)
	private String resourceName;

	/**
	 * 素材地址
	 */
	@Schema(description = "素材地址")
	@Length(max=255)
	private String artworkUrl;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private DeleteFlag delFlag;

	/**
	 * oss服务器类型，对应system_config的config_type
	 */
	@Schema(description = "oss服务器类型，对应system_config的config_type")
	@Length(max=255)
	private String serverType;

}