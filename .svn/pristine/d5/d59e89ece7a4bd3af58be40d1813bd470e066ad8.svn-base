package com.wanmi.sbc.goods.api.request.goodsaudit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询商品审核请求参数</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAuditByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * goodsId
	 */
	@Schema(description = "goodsId")
	@NotNull
	private String goodsId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识", hidden = true)
	private Long storeId;

}