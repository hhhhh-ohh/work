package com.wanmi.sbc.customer.api.request.liveroomreplay;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询直播回放请求参数</p>
 * @author zwb
 * @date 2020-06-17 09:24:26
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomReplayByRoomIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 直播房间id
	 */
	@Schema(description = "直播房间id")
	@NotNull
	private Long roomId;

}