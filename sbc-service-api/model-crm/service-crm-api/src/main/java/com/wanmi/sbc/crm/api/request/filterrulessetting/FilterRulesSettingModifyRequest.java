package com.wanmi.sbc.crm.api.request.filterrulessetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.bean.dto.FilterRulesSettingDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>修改参数</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRulesSettingModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 设置参数
	 */
	@Schema(description = "设置参数")
	@NotEmpty
	@Size(max = 3, min = 3)
	private List<FilterRulesSettingDTO> filterRulesSettingDTOList;

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

	public void checkParam(){
		//仅限20-2000间10的整倍数
		filterRulesSettingDTOList.forEach(dto -> {
			Integer num = dto.getNum();
			if (Objects.nonNull(num) && num % 10 != 0){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		});
	}
}