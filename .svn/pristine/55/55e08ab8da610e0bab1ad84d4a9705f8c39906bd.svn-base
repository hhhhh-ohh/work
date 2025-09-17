package com.wanmi.sbc.message.api.request.minimsgactivitysetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>小程序订阅消息配置表通用查询请求参数</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgActivitySettingQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

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
	 * 搜索条件:开始时间开始
	 */
	@Schema(description = "搜索条件:开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeBegin;
	/**
	 * 搜索条件:开始时间截止
	 */
	@Schema(description = "搜索条件:开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeEnd;

	/**
	 * 搜索条件:结束时间开始
	 */
	@Schema(description = "搜索条件:结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeBegin;
	/**
	 * 搜索条件:结束时间截止
	 */
	@Schema(description = "搜索条件:结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeEnd;

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
	 * 搜索条件:定时发送时间开始
	 */
	@Schema(description = "搜索条件:定时发送时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTimeBegin;
	/**
	 * 搜索条件:定时发送时间截止
	 */
	@Schema(description = "搜索条件:定时发送时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTimeEnd;

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
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

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
	 * 删除标识,0: 未删除 1: 已删除
	 */
	@Schema(description = "删除标识,0: 未删除 1: 已删除")
	private DeleteFlag delFlag;

	/**
	 * 是否已经扫描到 false 否  true 是
	 */
	@Schema(description = "是否已经扫描到 false 否  true 是")
	private Boolean scanFlag;

}