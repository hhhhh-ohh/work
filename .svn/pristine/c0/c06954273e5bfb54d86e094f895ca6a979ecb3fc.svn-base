package com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration;

import com.wanmi.sbc.vas.api.request.VasBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除推荐坑位设置请求参数</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:04:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPositionConfigurationDelByIdListRequest extends VasBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键idList
	 */
	@Schema(description = "批量删除-主键idList")
	@NotEmpty
	private List<Long> idList;
}
