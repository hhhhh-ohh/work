package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>直播房间和直播商品关联表VO</p>
 * @author zwb
 * @date 2020-06-08 09:12:17
 */
@Schema
@Data
public class LiveRoomLiveGoodsRelVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 直播房间id
	 */
	@Schema(description = "直播房间id")
	private Long roomId;

	/**
	 * 直播商品id
	 */
	@Schema(description = "直播商品id")
	private Long goodsId;

}