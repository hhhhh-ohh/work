package com.wanmi.sbc.empower.api.request.ledgermcc;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除拉卡拉mcc表请求参数</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerMccDelByIdListRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-mcc编号List
	 */
	@Schema(description = "批量删除-mcc编号List")
	@NotEmpty
	private List<Long> mccIdList;
}
