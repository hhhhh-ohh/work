package com.wanmi.sbc.empower.api.request.apppush;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除消息推送配置请求参数</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushSettingDelByIdListRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<Integer> idList;
}
