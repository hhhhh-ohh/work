package com.wanmi.sbc.goods.api.request.flashsalecate;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除秒杀分类请求参数</p>
 * @author yxz
 * @date 2019-06-11 10:11:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleCateDelByIdRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 秒杀分类主键
	 */
	@Schema(description = "秒杀分类主键")
	@NotNull
	private Long cateId;
}