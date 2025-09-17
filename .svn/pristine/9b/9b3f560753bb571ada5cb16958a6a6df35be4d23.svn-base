package com.wanmi.sbc.goods.api.request.goodslabel;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>批量删除商品标签请求参数</p>
 * @author dyt
 * @date 2020-09-29 13:57:19
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsLabelDelByIdListRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-标签idList
	 */
	@Schema(description = "批量删除-标签idList")
	@NotEmpty
	private List<Long> goodsLabelIdList;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id", hidden = true)
	private Long storeId;
}