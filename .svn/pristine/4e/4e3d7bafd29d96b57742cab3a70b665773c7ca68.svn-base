package com.wanmi.sbc.marketing.api.request.communityregionsetting;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区拼团区域设置表分页查询请求参数</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionSettingPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-区域idList
	 */
	@Schema(description = "批量查询-区域idList")
	private List<Long> regionIdList;

	/**
	 * 区域id
	 */
	@Schema(description = "区域id")
	private Long regionId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 区域名称
	 */
	@Schema(description = "区域名称")
	private String regionName;
}