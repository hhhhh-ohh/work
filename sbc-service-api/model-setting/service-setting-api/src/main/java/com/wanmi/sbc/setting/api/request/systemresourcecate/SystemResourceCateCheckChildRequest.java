package com.wanmi.sbc.setting.api.request.systemresourcecate;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>平台素材资源分类列表查询请求参数</p>
 * @author lq
 * @date 2019-11-05 16:14:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceCateCheckChildRequest extends BaseQueryRequest {
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