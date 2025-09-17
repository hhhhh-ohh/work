package com.wanmi.sbc.customer.api.request.communityleader;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>社区团购团长表列表查询请求参数</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-团长idList
	 */
	@Schema(description = "批量查询-团长idList")
	private List<String> leaderIdList;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	private String leaderId;

	/**
	 * 团长账号
	 */
	@Schema(description = "团长账号")
	private String leaderAccount;

	/**
	 * 模糊-团长账号
	 */
	@Schema(description = "模糊-团长账号")
	private String likeLeaderAccount;

	/**
	 * 模糊-团长名称
	 */
	@Schema(description = "模糊团长名称")
	private String likeLeaderName;

	/**
	 * 团长简介
	 */
	@Schema(description = "团长简介")
	private String leaderDescription;

	/**
	 * 审核状态, 0:未审核 1:审核通过 2:审核失败 3:禁用中
	 */
	@Schema(description = "审核状态, 0:未审核 1:审核通过 2:审核失败 3:禁用中")
	private LeaderCheckStatus checkStatus;

	/**
	 * 驳回原因
	 */
	@Schema(description = "驳回原因")
	private String checkReason;

	/**
	 * 禁用原因
	 */
	@Schema(description = "禁用原因")
	private String disableReason;

	/**
	 * 搜索条件:审核时间开始
	 */
	@Schema(description = "搜索条件:审核时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime checkTimeBegin;
	/**
	 * 搜索条件:审核时间截止
	 */
	@Schema(description = "搜索条件:审核时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime checkTimeEnd;

	/**
	 * 搜索条件:禁用时间开始
	 */
	@Schema(description = "搜索条件:禁用时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime disableTimeBegin;
	/**
	 * 搜索条件:禁用时间截止
	 */
	@Schema(description = "搜索条件:禁用时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime disableTimeEnd;

	/**
	 * 是否帮卖 0:否 1:是
	 */
	@Schema(description = "是否帮卖 0:否 1:是")
	private Integer assistFlag;

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
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 自提点地区id
	 */
	@Schema(description = "自提点地区id")
	private List<String> areaIds;

	/**
	 * 是否额外提供自提点地区
	 */
	@Schema(description = "是否额外提供自提点地区", hidden = true)
	private Boolean pickupFlag;

	/**
	 * 删除标识
	 */
	@Schema(description = "删除标识")
	private DeleteFlag delFlag;
}