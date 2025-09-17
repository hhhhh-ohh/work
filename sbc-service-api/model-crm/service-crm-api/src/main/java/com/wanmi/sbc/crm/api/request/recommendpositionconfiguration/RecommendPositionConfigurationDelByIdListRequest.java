package com.wanmi.sbc.crm.api.request.recommendpositionconfiguration;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

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
public class RecommendPositionConfigurationDelByIdListRequest extends CrmBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键idList
	 */
	@Schema(description = "批量删除-主键idList")
	@NotEmpty
	private List<Long> idList;
}
