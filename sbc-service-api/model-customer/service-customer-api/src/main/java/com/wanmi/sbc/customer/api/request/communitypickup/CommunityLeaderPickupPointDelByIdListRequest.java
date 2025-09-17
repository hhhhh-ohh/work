package com.wanmi.sbc.customer.api.request.communitypickup;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除团长自提点表请求参数</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderPickupPointDelByIdListRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-自提点idList
	 */
	@Schema(description = "批量删除-自提点idList")
	@NotEmpty
	private List<String> pickupPointIdList;
}
