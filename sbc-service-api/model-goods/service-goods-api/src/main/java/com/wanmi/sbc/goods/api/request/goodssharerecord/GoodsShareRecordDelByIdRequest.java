package com.wanmi.sbc.goods.api.request.goodssharerecord;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除商品分享请求参数</p>
 * @author zhangwenchang
 * @date 2020-03-06 13:46:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsShareRecordDelByIdRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * shareId
	 */
	@Schema(description = "shareId")
	@NotNull
	private Long shareId;
}