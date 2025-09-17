package com.wanmi.sbc.customer.api.request.communityleader;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除社区团购团长表请求参数</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderDelByIdListRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-团长idList
	 */
	@Schema(description = "批量删除-团长idList")
	@NotEmpty
	private List<String> leaderIdList;
}
