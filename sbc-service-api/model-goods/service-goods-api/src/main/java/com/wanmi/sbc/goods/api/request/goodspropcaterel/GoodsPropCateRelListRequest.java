package com.wanmi.sbc.goods.api.request.goodspropcaterel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>商品类目与属性关联列表查询请求参数</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropCateRelListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	@Schema(description = "类目属性")
	@NotNull
	private Long cateId;

}