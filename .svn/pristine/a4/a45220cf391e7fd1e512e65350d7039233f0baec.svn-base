package com.wanmi.sbc.message.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>小程序订阅消息配置表VO</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@Schema
@Data
public class MiniMsgActivitySettingVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	private String activityName;

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
	 * 活动内容
	 */
	@Schema(description = "活动内容")
	private String context;

	/**
	 * 温馨提示
	 */
	@Schema(description = "温馨提示")
	private String tips;

	/**
	 * 要跳转的页面
	 */
	@Schema(description = "要跳转的页面")
	private String toPage;

	/**
	 * 推送类型 0 立即发送  1 定时发送
	 */
	@Schema(description = "推送类型 0 立即发送  1 定时发送")
	private Integer type;

	/**
	 * 定时发送时间
	 */
	@Schema(description = "定时发送时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

	/**
	 * 预计推送人数
	 */
	@Schema(description = "预计推送人数")
	private Integer preCount;

	/**
	 * 实际推送人数
	 */
	@Schema(description = "实际推送人数")
	private Integer realCount;

	/**
	 * 推送状态 0：未推送，1：推送中，2：已推送，3：推送失败，4：部分失败
	 */
	@Schema(description = "推送状态 0：未推送，1：推送中，2：已推送，3：推送失败，4：部分失败")
	private ProgramSendStatus sendStatus;

	/**
	 * 删除标识,0: 未删除 1: 已删除
	 */
	@Schema(description = "删除标识,0: 未删除 1: 已删除")
	private DeleteFlag delFlag;

}