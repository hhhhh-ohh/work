package com.wanmi.sbc.marketing.api.request.communityregionsetting;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>社区拼团商家设置表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-20 14:56:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionAreaSettingQueryRequest extends BaseQueryRequest {
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
	 * 省市区id
	 */
	@Schema(description = "省市区id")
	private String areaId;

	/**
	 * 批量-省市区id
	 */
	@Schema(description = "省市区id")
	private List<String> areaIds;

	/**
	 * 省市区名称
	 */
	@Schema(description = "省市区名称")
	private String areaName;

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
}