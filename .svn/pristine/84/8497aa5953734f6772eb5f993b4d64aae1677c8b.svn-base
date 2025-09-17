package com.wanmi.sbc.crm.api.request.autotagother;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>偏好标签明细分页查询请求参数</p>
 * @author dyt
 * @date 2020-03-11 14:58:07
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoTagOtherPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签id
	 */
	@Schema(description = "标签id")
	private Long tagId;

	/**
	 * 偏好类标签名称
	 */
	@Schema(description = "偏好类标签名称")
	private String detailName;

	@Schema(description = "标签类型")
	private int type;
}