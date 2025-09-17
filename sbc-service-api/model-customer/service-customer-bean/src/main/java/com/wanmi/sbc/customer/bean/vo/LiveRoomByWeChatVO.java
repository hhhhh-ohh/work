package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>直播间VO</p>
 * @author zwb
 * @date 2020-06-06 18:28:57
 */
@Schema
@Data
public class LiveRoomByWeChatVO extends BasicResponse {
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
	 * 直播商品列表结果
	 */
	@Schema(description = "直播商品列表结果")
	private List<LiveGoodsByWeChatVO> goods;


	/**
	 * 直播房间名
	 */
	@Schema(description = "直播房间名")
	private String name;

	/**
	 * 是否推荐
	 */
	@Schema(description = "是否推荐")
	private Integer recommend;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	private Long startTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	private Long endTime;

	/**
	 * 主播昵称
	 */
	@Schema(description = "主播昵称")
	private String anchorName;

	/**
	 * 主播微信
	 */
	@Schema(description = "主播微信")
	private String anchorWechat;

	/**
	 * 直播背景墙
	 */
	@Schema(description = "直播背景墙")
	private String coverImg;

	/**
	 * 分享卡片封面
	 */
	@Schema(description = "分享卡片封面")
	private String shareImg;

	/**
	 * 直播状态 0: 直播中, 1: 未开始, 2: 已结束, 3: 禁播, 4: 暂停中, 5: 异常, 6: 已过期
	 */
	@Schema(description = "直播状态 0: 直播中, 1: 未开始, 2: 已结束, 3: 禁播, 4: 暂停中, 5: 异常, 6: 已过期")
	private Integer liveStatus;

	/**
	 * 直播类型，1：推流，0：手机直播
	 */
	@Schema(description = "直播类型，1：推流，0：手机直播")
	private Integer type;

	/**
	 * 1：横屏，0：竖屏
	 */
	@Schema(description = "1：横屏，0：竖屏")
	private Integer screenType;

	/**
	 * 1：关闭点赞 0：开启点赞，关闭后无法开启
	 */
	@Schema(description = "1：关闭点赞 0：开启点赞，关闭后无法开启")
	private Integer closeLike;

	/**
	 * 1：关闭货架 0：打开货架，关闭后无法开启
	 */
	@Schema(description = "1：关闭货架 0：打开货架，关闭后无法开启")
	private Integer closeGoods;

	/**
	 * 1：关闭评论 0：打开评论，关闭后无法开启
	 */
	@Schema(description = "1：关闭评论 0：打开评论，关闭后无法开启")
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
	private String liveCompanyId;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

}