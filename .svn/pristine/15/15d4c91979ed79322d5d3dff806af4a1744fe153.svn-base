package com.wanmi.sbc.marketing.api.request.communitysetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderAreaSummaryType;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.util.List;

/**
 * <p>社区拼团商家设置表修改参数</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySettingModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id", hidden = true)
	private Long storeId;

	/**
	 * 汇总类型 0:按团长 1:按区域 以逗号拼凑
	 */
	@Schema(description = "汇总类型 0:按团长 1:按区域")
	private List<DeliveryOrderSummaryType> deliveryOrderTypes;

	/**
	 * 区域汇总类型 0：省份1：城市2：自定义
	 */
	@Schema(description = "区域汇总类型 0：省份1：城市2：自定义")
	private DeliveryOrderAreaSummaryType deliveryAreaType;
}
