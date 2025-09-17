package com.wanmi.sbc.marketing.api.request.communitysetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询社区拼团商家设置表请求参数</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySettingByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	@NotNull
	private Long storeId;

}