package com.wanmi.sbc.goods.api.request.liveroomlivegoodsrel;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>直播房间和直播商品关联表新增参数</p>
 * @author zwb
 * @date 2020-06-08 09:12:17
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomLiveGoodsRelAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 直播房间id
	 */
	@Schema(description = "直播房间id")
	@NotNull
	@Max(9223372036854775807L)
	private Long roomId;

	/**
	 * 直播商品id
	 */
	@Schema(description = "直播商品id")
	@NotNull
	@Max(9223372036854775807L)
	private Long goodsId;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除", hidden = true)
	private DeleteFlag delFlag;

}