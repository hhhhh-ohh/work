package com.wanmi.sbc.setting.api.request.appexternalconfig;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除AppExternalConfig请求参数</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalConfigDelByIdListRequest extends SettingBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-idList
	 */
	@Schema(description = "批量删除-idList")
	@NotEmpty
	private List<Long> idList;
}
