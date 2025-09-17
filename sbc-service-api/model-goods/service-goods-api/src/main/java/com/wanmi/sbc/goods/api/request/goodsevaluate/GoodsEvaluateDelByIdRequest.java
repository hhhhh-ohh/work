package com.wanmi.sbc.goods.api.request.goodsevaluate;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除商品评价请求参数</p>
 * @author liutao
 * @date 2019-02-25 15:17:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsEvaluateDelByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 评价id
	 */
	@NotNull
	private String evaluateId;
}