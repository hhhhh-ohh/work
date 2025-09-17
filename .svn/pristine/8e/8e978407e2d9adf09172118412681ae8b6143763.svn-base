package com.wanmi.sbc.marketing.api.request.communityassist;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>单个查询社区团购活动帮卖转发记录请求参数</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityAssistRecordByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotBlank
	private String id;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id", hidden = true)
	private Long storeId;

}