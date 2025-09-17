package com.wanmi.sbc.crm.api.request.recommendsystemconfig;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除智能推荐配置请求参数</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendSystemConfigDelByIdListRequest extends CrmBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除- 编号List
	 */
	@Schema(description = "批量删除- 编号List")
	@NotEmpty
	private List<Long> idList;
}
