package com.wanmi.sbc.crm.api.request.goodscorrelationmodelsetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.bean.dto.GoodsCorrelationModelSettingDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>修改参数</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCorrelationModelSettingModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数
	 */
	@Schema(description = "参数")
	@NotEmpty
	@Size(max = 4, min = 4)
	private List<GoodsCorrelationModelSettingDTO> filterRulesSettingDTOList;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}