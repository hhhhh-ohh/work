package com.wanmi.sbc.marketing.api.request.communitydeliveryorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.CommunityDeliveryOrderDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>社区团购发货单新增参数</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDeliveryOrderAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量-发货单数据
	 */
	@Schema(description = "活动id")
	@NotEmpty
	private List<CommunityDeliveryOrderDTO> communityDeliveryOrderDTOList;

}