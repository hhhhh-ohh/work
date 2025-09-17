package com.wanmi.sbc.crm.api.request.tagdimension;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>标签维度分页查询请求参数</p>
 * @author dyt
 * @date 2020-03-12 16:00:30
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDimensionPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 维度名
	 */
	@Schema(description = "维度名")
	private String name;

	/**
	 * 维度对应表明
	 */
	@Schema(description = "维度对应表明")
	private String tableName;

}