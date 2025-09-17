package com.wanmi.sbc.setting.api.request.systemresourcecate;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>单个删除平台素材资源分类请求参数</p>
 * @author lq
 * @date 2019-11-05 16:14:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceCateDelByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 素材资源分类id
	 */
	@Schema(description = "素材资源分类id")
	@NotNull
	private Long cateId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	private Long storeId;
}