package com.wanmi.sbc.setting.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * <p>AppExternalConfigVO</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@Schema
@Data
public class AppExternalConfigVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 小程序名称
	 */
	@Schema(description = "小程序名称")
	private String appName;

	/**
	 * 小程序appId
	 */
	@Schema(description = "小程序appId")
	private String appId;

	/**
	 * 原始Id
	 */
	@Schema(description = "原始Id")
	private String originalId;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	@Schema(description = "小程序页面配置")
	private List<AppExternalLinkVO> appExternalLinks;
}