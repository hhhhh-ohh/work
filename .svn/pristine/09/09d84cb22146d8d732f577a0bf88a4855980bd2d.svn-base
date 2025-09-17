package com.wanmi.sbc.marketing.api.request.communityregionsetting;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区拼团商家设置表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-20 14:55:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionLeaderSettingQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 区域id
	 */
	@Schema(description = "区域id")
	private Long regionId;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	private String leaderId;

	/**
	 * 自提点id
	 */
	@Schema(description = "自提点id")
	private String pickupPointId;

	/**
	 * 区域id
	 */
	@Schema(description = "区域id")
	private List<Long> regionIds;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 非自身区域id
	 */
	@Schema(description = "非自身区域id")
	private Long notRegionId;

	/**
	 * 多个自提点id
	 */
	@Schema(description = "多个自提点id")
	private List<String> pickupPointIds;
}