package com.wanmi.sbc.customer.api.request.communityleader;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

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
public class CommunityLeaderCheckByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	@NotBlank
	private String leaderId;


	/**
	 * 审核状态
	 */
	@Schema(description = "审核状态")
	@NotNull
	private LeaderCheckStatus checkStatus;

	/**
	 * 审核原因
	 */
	@Schema(description = "审核原因")
	@Length(min = 1, max = 200)
	private String reason;

	@Override
	public void checkParam() {
		if ((LeaderCheckStatus.FORBADE.equals(checkStatus) || LeaderCheckStatus.NOT_PASS.equals(checkStatus))
				&& StringUtils.isBlank(reason)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}
}
