package com.wanmi.sbc.customer.api.request.liveroomreplay;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>直播回放新增参数</p>
 * @author zwb
 * @date 2020-06-17 09:24:26
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomReplayAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 视频过期时间
	 */
	@Schema(description = "视频过期时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expireTime;

	/**
	 * 视频回放路径
	 */
	@Schema(description = "视频回放路径")
	@Length(max=255)
	private String mediaUrl;

	/**
	 * 直播房间id
	 */
	@Schema(description = "直播房间id")
	@Max(9223372036854775807L)
	private Long roomId;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	@Length(max=255)
	private String deletePerson;

	/**
	 * 删除逻辑 0：未删除 1 已删除
	 */
	@Schema(description = "删除逻辑 0：未删除 1 已删除", hidden = true)
	private DeleteFlag delFlag;

}