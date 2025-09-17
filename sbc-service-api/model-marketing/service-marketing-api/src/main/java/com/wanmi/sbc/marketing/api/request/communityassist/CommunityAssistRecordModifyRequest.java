package com.wanmi.sbc.marketing.api.request.communityassist;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>社区团购活动帮卖转发记录修改参数</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityAssistRecordModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Length(max=32)
	private String id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	@NotBlank
	@Length(max=32)
	private String activityId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	@NotNull
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	@NotBlank
	@Length(max=32)
	private String leaderId;

}
