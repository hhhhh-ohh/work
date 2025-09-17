package com.wanmi.sbc.crm.api.request.filterrulessetting;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除请求参数</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRulesSettingDelByIdListRequest extends CrmBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<Integer> idList;
}
