package com.wanmi.sbc.marketing.api.request.communityregionsetting;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>社区拼团区域设置表列表查询请求参数</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionSettingUsedAreaRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 区域id
	 */
	@Schema(description = "区域id")
	private Long regionId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id", hidden = true)
	private Long storeId;

}