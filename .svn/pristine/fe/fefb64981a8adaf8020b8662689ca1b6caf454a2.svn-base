package com.wanmi.sbc.setting.api.request.pickupsetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询pickup_setting请求参数</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupSettingByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Long id;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID", hidden = true)
	private Long storeId;

}