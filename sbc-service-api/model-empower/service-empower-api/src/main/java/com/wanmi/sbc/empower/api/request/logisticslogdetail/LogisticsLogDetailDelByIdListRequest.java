package com.wanmi.sbc.empower.api.request.logisticslogdetail;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除物流记录明细请求参数</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogDetailDelByIdListRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<Long> idList;
}
