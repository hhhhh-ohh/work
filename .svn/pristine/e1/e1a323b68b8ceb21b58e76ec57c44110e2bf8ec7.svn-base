package com.wanmi.sbc.customer.api.request.liveroom;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个修改直播间是否推荐请求参数</p>
 * @author zwb
 * @date 2020-06-06 18:28:57
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomByRoomIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 直播间id
	 */
	@Schema(description = "直播间id")
	@NotNull
	private Long roomId;

	/**
	 * 是否推荐
	 */
	@Schema(description = "是否推荐")
	private Integer recommend;

}