package com.wanmi.sbc.customer.api.request.liveroomreplay;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>直播回放列表查询请求参数</p>
 * @author zwb
 * @date 2020-06-17 09:24:26
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomReplayListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键idList
	 */
	@Schema(description = "批量查询-主键idList")
	private List<Long> idList;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 搜索条件:视频过期时间开始
	 */
	@Schema(description = "搜索条件:视频过期时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expireTimeBegin;
	/**
	 * 搜索条件:视频过期时间截止
	 */
	@Schema(description = "搜索条件:视频过期时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expireTimeEnd;

	/**
	 * 搜索条件:视频创建时间开始
	 */
	@Schema(description = "搜索条件:视频创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:视频创建时间截止
	 */
	@Schema(description = "搜索条件:视频创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 视频回放路径
	 */
	@Schema(description = "视频回放路径")
	private String mediaUrl;

	/**
	 * 直播房间id
	 */
	@Schema(description = "直播房间id")
	private Long roomId;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 搜索条件:删除时间开始
	 */
	@Schema(description = "搜索条件:删除时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeBegin;
	/**
	 * 搜索条件:删除时间截止
	 */
	@Schema(description = "搜索条件:删除时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeEnd;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

	/**
	 * 删除逻辑 0：未删除 1 已删除
	 */
	@Schema(description = "删除逻辑 0：未删除 1 已删除")
	private DeleteFlag delFlag;



}