package com.wanmi.sbc.goods.api.request.liveroomlivegoodsrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.Map;

/**
 * <p>单个查询直播房间和直播商品关联表请求参数</p>
 * @author zwb
 * @date 2020-06-08 09:12:17
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomLiveGoodsRelListByRoomIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * Map
	 */
	@Schema(description = "Map")
	@NotNull
	private Map<Long, Long> roomIdAndStoreIdMap;

}