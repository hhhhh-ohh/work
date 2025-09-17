package com.wanmi.sbc.customer.api.request.ledgerfile;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除分账文件请求参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFileDelByIdListRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-idList
	 */
	@Schema(description = "批量删除-idList")
	@NotEmpty
	private List<String> idList;
}
