package com.wanmi.sbc.goods.api.request.goodsproperty;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据商品id查询商品属性请求参数</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyByGoodsIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	@NotNull
	private String goodsId;
	@Schema(description = "会员等级")
	private String propName;
}