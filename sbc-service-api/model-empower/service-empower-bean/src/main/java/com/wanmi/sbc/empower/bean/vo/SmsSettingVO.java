package com.wanmi.sbc.empower.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.bean.enums.SmsPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>短信配置VO</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:15:28
 */
@Schema
@Data
public class SmsSettingVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

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
	 * 华信接口地址
	 */
	@Schema(description = "华信接口地址")
	private String huaxinApiUrl;

	/**
	 * 华信短信报备和签名
	 */
	@Schema(description = "华信短信报备和签名")
	private String huaxinTemplate;

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

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

}