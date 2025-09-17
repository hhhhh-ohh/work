package com.wanmi.sbc.crm.api.request.tagparam;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.crm.bean.enums.TagDimensionFirstLastType;
import com.wanmi.sbc.crm.bean.enums.TagType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

/**
 * <p>标签参数列表查询请求参数</p>
 * @author dyt
 * @date 2020-03-12 15:59:49
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagParamListRequest extends BaseQueryRequest {
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
	 * 标签维度id
	 */
	@Schema(description = "标签维度id")
	private Long tagDimensionId;

    /**
     * 批量查询-标签维度idList
     */
    @Schema(description = "批量查询-标签维度idList")
    private List<Long> tagDimensionIdList;

	/**
	 * 维度配置名称
	 */
	@Schema(description = "维度配置名称")
	private String name;

	/**
	 * 字段名称
	 */
	@Schema(description = "字段名称")
	private String columnName;

	/**
	 * 维度配置类型，0：top类型，1：聚合结果类型，2：查询条件类型
	 */
	@Schema(description = "维度配置类型，0：top类型，1：聚合结果类型，2：查询条件类型")
	private Integer type;

	/**
	 * 标签维度类型，0：top类型，1：count计数类型，2：sum求和，3：avg平均值，4：max最大值，5：min最小值，6：in包含类型，7：等于，8、区间类，9：多重期间or关系
	 */
	@Schema(description = "标签维度类型，0：top类型，1：count计数类型，2：sum求和，3：avg平均值，4：max最大值，5：min最小值，6：in包含类型，7：等于，8、区间类，9：多重期间or关系")
	private Integer tagDimensionType;

    /**
     * 标签类型 1:指标值参数
     */
	@Schema(description = "标签类型 0: 偏好类 1：标值 2：指标值范围 3：综合")
    private TagType tagType;

	/**
	 * 首次末次类型
	 */
	@Schema(description = "首次末次类型")
	private TagDimensionFirstLastType firstLastType;
}