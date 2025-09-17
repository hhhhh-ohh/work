package com.wanmi.sbc.message.api.request.pushcustomerenable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>会员推送通知开关分页查询请求参数</p>
 * @author Bob
 * @date 2020-01-07 15:31:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushCustomerEnablePageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-会员IDList
	 */
	@Schema(description = "批量查询-会员IDList")
	private List<String> customerIdList;

	/**
	 * 会员ID
	 */
	@Schema(description = "会员ID")
	private String customerId;

	/**
	 * 开启状态 0:未开启 1:启用
	 */
	@Schema(description = "开启状态 0:未开启 1:启用")
	private Integer enableStatus;

	/**
	 * 账号安全通知 0:未启用 1:启用
	 */
	@Schema(description = "账号安全通知 0:未启用 1:启用")
	private Integer accountSecurity;

	/**
	 * 账户资产通知 0:未启用 1:启用
	 */
	@Schema(description = "账户资产通知 0:未启用 1:启用")
	private Integer accountAssets;

	/**
	 * 订单进度通知 0:未启用 1:启用
	 */
	@Schema(description = "订单进度通知 0:未启用 1:启用")
	private Integer orderProgressRate;

	/**
	 * 退单进度通知 0:未启用 1:启用
	 */
	@Schema(description = "退单进度通知 0:未启用 1:启用")
	private Integer returnOrderProgressRate;

	/**
	 * 分销业务通知 0:未启用 1:启用
	 */
	@Schema(description = "分销业务通知 0:未启用 1:启用")
	private Integer distribution;

	/**
	 * 删除标志 0:未删除 1:删除
	 */
	@Schema(description = "删除标志 0:未删除 1:删除", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人ID
	 */
	@Schema(description = "创建人ID")
	private String createPerson;

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
	 * 更新人ID
	 */
	@Schema(description = "更新人ID")
	private String updatePerson;

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

}