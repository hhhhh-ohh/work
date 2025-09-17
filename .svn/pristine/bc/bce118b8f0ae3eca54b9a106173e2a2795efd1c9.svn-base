package com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>智能推荐配置新增参数</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendSystemConfigAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 键
	 */
	@Schema(description = "键")
	@Length(max=255)
	private String configKey;

	/**
	 * 类型
	 */
	@Schema(description = "类型")
	@Length(max=255)
	private String configType;

	/**
	 * 名称
	 */
	@Schema(description = "名称")
	@Length(max=255)
	private String configName;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	@Length(max=255)
	private String remark;

	/**
	 * 状态,0:未启用1:已启用
	 */
	@Schema(description = "状态,0:未启用1:已启用")
	private Integer status;

	/**
	 * 配置内容，如JSON内容
	 */
	@Schema(description = "配置内容，如JSON内容")
	@Length(max=2147483647)
	private String context;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}