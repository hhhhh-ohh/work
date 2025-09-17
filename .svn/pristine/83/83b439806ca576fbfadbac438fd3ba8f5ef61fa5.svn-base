package com.wanmi.sbc.crm.api.request.tagdimension;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除标签维度请求参数</p>
 * @author dyt
 * @date 2020-03-12 16:00:30
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDimensionDelByIdListRequest extends CrmBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-idList
	 */
	@Schema(description = "批量删除-idList")
	@NotEmpty
	private List<Long> idList;
}
