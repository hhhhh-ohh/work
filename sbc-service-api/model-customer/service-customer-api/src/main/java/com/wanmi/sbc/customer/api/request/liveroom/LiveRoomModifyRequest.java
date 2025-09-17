package com.wanmi.sbc.customer.api.request.liveroom;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>直播间修改参数</p>
 * @author zwb
 * @date 2020-06-06 18:28:57
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 直播房间id
	 */
	@Schema(description = "直播房间id")
	@NotNull
	@Max(9223372036854775807L)
	private Long roomId;

	/**
	 * 直播房间名
	 */
	@Schema(description = "直播房间名")
	@Length(max=255)
	private String name;

	/**
	 * 是否推荐
	 */
	@Schema(description = "是否推荐")
	@Length(max=255)
	private Integer recommend;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 主播昵称
	 */
	@Schema(description = "主播昵称")
	@Length(max=255)
	private String anchorName;

	/**
	 * 主播微信
	 */
	@Schema(description = "主播微信")
	@Length(max=255)
	private String anchorWechat;

	/**
	 * 直播背景墙
	 */
	@IsImage
	@Schema(description = "直播背景墙")
	@Length(max=255)
	private String coverImg;

	/**
	 * 分享卡片封面
	 */
	@IsImage
	@Schema(description = "分享卡片封面")
	@Length(max=255)
	private String shareImg;

	/**
	 * 直播状态 0: 直播中, 1: 未开始, 2: 已结束, 3: 禁播, 4: 暂停中, 5: 异常, 6: 已过期
	 */
	@Schema(description = "直播状态 0: 直播中, 1: 未开始, 2: 已结束, 3: 禁播, 4: 暂停中, 5: 异常, 6: 已过期")
	@Max(127)
	private Integer liveStatus;

	/**
	 * 直播类型，1：推流，0：手机直播
	 */
	@Schema(description = "直播类型，1：推流，0：手机直播")
	@Max(127)
	private Integer type;

	/**
	 * 1：横屏，0：竖屏
	 */
	@Schema(description = "1：横屏，0：竖屏")
	@Max(127)
	private Integer screenType;

	/**
	 * 1：关闭点赞 0：开启点赞，关闭后无法开启
	 */
	@Schema(description = "1：关闭点赞 0：开启点赞，关闭后无法开启")
	@Max(127)
	private Integer closeLike;

	/**
	 * 1：关闭货架 0：打开货架，关闭后无法开启
	 */
	@Schema(description = "1：关闭货架 0：打开货架，关闭后无法开启")
	@Max(127)
	private Integer closeGoods;

	/**
	 * 1：关闭评论 0：打开评论，关闭后无法开启
	 */
	@Schema(description = "1：关闭评论 0：打开评论，关闭后无法开启")
	@Max(127)
	private Integer closeComment;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 直播商户id
	 */
	@Schema(description = "直播商户id")
	@Length(max=255)
	private String liveCompanyId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	@Length(max=255)
	private String deletePerson;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;


}