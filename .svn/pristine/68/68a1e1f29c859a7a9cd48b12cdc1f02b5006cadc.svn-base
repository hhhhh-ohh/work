package com.wanmi.sbc.goods.api.request.livegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.util.List;

/**
 * <p>直播商品新增参数</p>
 * @author zwb
 * @date 2020-06-10 11:05:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	/**
	 * accessToken
	 */
	@Schema(description = "accessToken")
	@Length(max=255)
	private String accessToken;

	/**
	 * 直播间id
	 */
	@Schema(description = "直播间id")
	@Max(9223372036854775807L)
	private Long roomId;


	/**
	 * 直播商品idList
	 */
	@Schema(description = "直播商品idList")
	private List<Long> goodsIdList;




}