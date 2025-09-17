package com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * <p>微信视频号带货商品新增参数</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatSkuAddRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * goodsInfoId
	 */
	@Schema(description = "goodsInfoId")
	@NotNull
	private List<String> goodsInfoIds;

	private Long storeId;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson")
	@Length(max=32)
	private String createPerson;

}