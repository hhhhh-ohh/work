package com.wanmi.sbc.customer.api.request.communityleader;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * <p>社区团购团长表修改参数</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderModifyAssistByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	@NotEmpty
	private List<String> leaderIds;


	/**
	 * 是否帮卖 0:否 1:是
	 */
	@Schema(description = "是否帮卖 0:否 1:是")
	@NotNull
	private Integer assistFlag;
}
