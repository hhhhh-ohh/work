package com.wanmi.sbc.goods.api.request.goodstobeevaluate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除订单商品待评价请求参数</p>
 * @author lzw
 * @date 2019-03-20 14:47:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTobeEvaluateDelByIdRequest extends BaseRequest {

	private static final long serialVersionUID = -2402505054385106935L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull
	private String id;
}