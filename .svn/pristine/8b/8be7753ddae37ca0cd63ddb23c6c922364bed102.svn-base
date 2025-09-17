package com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除微信视频号带货商品请求参数</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatSkuDelByGoodsIdRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String goodsId;

	@NotNull
	private Long storeId;

	private String operatorId = "";
}