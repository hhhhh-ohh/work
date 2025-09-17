package com.wanmi.sbc.crm.api.request.tagdimension;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.crm.bean.enums.TagType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * <p>标签维度列表查询请求参数</p>
 * @author dyt
 * @date 2020-03-12 16:00:30
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDimensionListRequest extends BaseQueryRequest {
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

	/**
	 * 标签类型
	 */
	@Schema(description = "标签类型 0: 偏好类 1：标值 2：指标值范围 3：综合")
	@NotNull
	private TagType tagType;



}