package com.wanmi.sbc.customer.api.request.communityleader;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointAddRequest;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * <p>社区团购团长表新增参数</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长账号
	 */
	@Schema(description = "团长账号")
	@NotBlank
	@Length(min = 11, max = 11)
	private String leaderAccount;

	/**
	 * 团长名称
	 */
	@Schema(description = "团长名称")
	@NotBlank
	@Length(min = 2, max = 30)
	private String leaderName;

	/**
	 * 团长简介
	 */
	@Schema(description = "团长简介")
	@Length(max = 200)
	private String leaderDescription;

	/**
	 * 默认审核状态
	 */
	@Schema(description = "默认审核状态")
	private LeaderCheckStatus checkStatus;

	/**
	 * 自提点
	 */
	@Schema(description = "自提点列表")
	@NotEmpty
	@Valid
	private List<CommunityLeaderPickupPointAddRequest> pickupPointList;

	@Override
	public void checkParam() {
		// 如果区号没有，判断长度是不是11
		if (CollectionUtils.isNotEmpty(pickupPointList)
				&& pickupPointList.stream().anyMatch(c -> StringUtils.isBlank(c.getContactCode()) && c.getContactNumber().length() != 11)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}
}