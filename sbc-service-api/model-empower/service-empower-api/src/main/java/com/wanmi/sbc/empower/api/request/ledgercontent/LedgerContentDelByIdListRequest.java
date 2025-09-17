package com.wanmi.sbc.empower.api.request.ledgercontent;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除拉卡拉经营内容表请求参数</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContentDelByIdListRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-经营内容idList
	 */
	@Schema(description = "批量删除-经营内容idList")
	@NotEmpty
	private List<Long> contentIdList;
}
