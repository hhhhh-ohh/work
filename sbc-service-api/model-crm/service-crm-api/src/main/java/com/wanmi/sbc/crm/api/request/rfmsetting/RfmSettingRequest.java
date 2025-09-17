package com.wanmi.sbc.crm.api.request.rfmsetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.api.request.CrmBaseRequest;
import com.wanmi.sbc.crm.bean.enums.Period;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>rfm参数配置新增参数</p>
 * @author zhanglingke
 * @date 2019-10-14 14:33:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RfmSettingRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * R配置
	 */
	@Schema(description = "R分析配置")
	@NotEmpty
	@Valid
	private List<RfmSettingRElementRequest> r;

	/**
	 * F配置
	 */
	@Schema(description = "F分析配置")
	@NotEmpty
	@Valid
	private List<RfmSettingFElementRequest> f;

	/**
	 * M配置
	 */
	@Schema(description = "M分析配置")
	@NotEmpty
	@Valid
	private List<RfmSettingMElementRequest> m;

	/**
	 * M配置
	 */
	@Schema(description = "统计范围")
	@NotNull
	private Period period;

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
	@Length(max=50)
	private String createPerson;

}