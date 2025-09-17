package com.wanmi.sbc.goods.api.request.goodslabel;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>商品标签修改参数</p>
 * @author dyt
 * @date 2020-09-29 13:57:19
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsLabelModifyVisibleRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签id
	 */
	@NotNull
	@Schema(description = "标签id")
	private Long goodsLabelId;

	/**
	 * 前端是否展示 false: 关闭 true:开启
	 */
	@NotNull
	@Schema(description = "前端是否展示 false: 关闭 true:开启")
	private Boolean labelVisible;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id", hidden = true)
	private Long storeId;
}