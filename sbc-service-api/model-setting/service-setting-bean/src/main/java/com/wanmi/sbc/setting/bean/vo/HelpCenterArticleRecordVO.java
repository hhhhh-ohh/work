package com.wanmi.sbc.setting.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>帮助中心文章记录VO</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@Schema
@Data
public class HelpCenterArticleRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	private Long id;

	/**
	 * 文章id
	 */
	@Schema(description = "文章id")
	private Long articleId;

	/**
	 * customerId
	 */
	@Schema(description = "customerId")
	private String customerId;

	/**
	 * 解决状态  0：未解决，1：已解决
	 */
	@Schema(description = "解决状态  0：未解决，1：已解决")
	private DefaultFlag solveType;

	/**
	 * 解决时间
	 */
	@Schema(description = "解决时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime solveTime;

}