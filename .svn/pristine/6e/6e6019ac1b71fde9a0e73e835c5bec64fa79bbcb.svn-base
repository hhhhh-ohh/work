package com.wanmi.sbc.customer.api.request.ledgerfile;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账文件通用查询请求参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFileQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 文件内容
	 */
	@Schema(description = "文件内容")
	private byte[] content;

}