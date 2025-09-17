package com.wanmi.sbc.marketing.api.request.communitysetting;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区拼团商家设置表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySettingQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-店铺idList
	 */
	@Schema(description = "批量查询-店铺idList")
	private List<Long> storeIdList;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 汇总类型 0:按团长 1:按区域 以逗号拼凑
	 */
	@Schema(description = "汇总类型 0:按团长 1:按区域 以逗号拼凑")
	private String deliveryOrderType;

	/**
	 * 区域汇总类型 0：省份1：城市2：自定义
	 */
	@Schema(description = "区域汇总类型 0：省份1：城市2：自定义")
	private Integer deliveryAreaType;

}