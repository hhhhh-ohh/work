package com.wanmi.sbc.empower.api.request.logisticslog;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除物流记录请求参数</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogDelByIdListRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<String> idList;
}
