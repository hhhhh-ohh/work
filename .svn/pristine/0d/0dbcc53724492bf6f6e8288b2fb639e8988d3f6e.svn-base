package com.wanmi.sbc.marketing.api.request.communityregionsetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除社区拼团区域设置表请求参数</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionSettingDelByIdListRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-区域idList
	 */
	@Schema(description = "批量删除-区域idList")
	@NotEmpty
	private List<Long> regionIdList;
}
