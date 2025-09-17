package com.wanmi.sbc.customer.api.request.communitypickup;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>团长自提点表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderPickupPointQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-自提点idList
	 */
	@Schema(description = "批量查询-自提点idList")
	private List<String> pickupPointIdList;

	/**
	 * 自提点id
	 */
	@Schema(description = "自提点id")
	private String pickupPointId;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	private String leaderId;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

	/**
	 * 多个团长id
	 */
	@Schema(description = "多个团长id")
	private List<String> leaderIds;

	/**
	 * 多个帮卖团长id
	 */
	@Schema(description = "多个帮卖团长id")
	private List<String> assistLeaderIds;

	/**
	 * 团长账号
	 */
	@Schema(description = "团长账号")
	private String leaderAccount;

	/**
	 * 模糊-团长账号
	 */
	@Schema(description = "团长账号")
	private String likeLeaderAccount;

	/**
	 * 模糊-团长名称
	 */
	@Schema(description = "模糊团长名称")
	private String likeLeaderName;

	/**
	 * 审核状态, 0:未审核 1审核通过 2审核失败 3禁用中
	 */
	@Schema(description = "审核状态, 0:未审核 1审核通过 2审核失败 3禁用中")
	private LeaderCheckStatus checkStatus;

	/**
	 * 帮卖标识
	 */
	@Schema(description = "帮卖标识")
	private Integer assistFlag;

	/**
	 * 自提点名称
	 */
	@Schema(description = "自提点名称")
	private String pickupPointName;

	/**
	 * 自提点省份
	 */
	@Schema(description = "自提点省份")
	private Long pickupProvinceId;

	/**
	 * 自提点城市
	 */
	@Schema(description = "自提点城市")
	private Long pickupCityId;

	/**
	 * 自提点区县
	 */
	@Schema(description = "自提点区县")
	private Long pickupAreaId;

	/**
	 * 自提点街道
	 */
	@Schema(description = "自提点街道")
	private Long pickupStreetId;

	/**
	 * 详细地址
	 */
	@Schema(description = "详细地址")
	private String address;

	/**
	 * 经度
	 */
	@Schema(description = "经度")
	private BigDecimal lng;

	/**
	 * 纬度
	 */
	@Schema(description = "纬度")
	private BigDecimal lat;

	/**
	 * 联系电话
	 */
	@Schema(description = "联系电话")
	private String contactNumber;

	/**
	 * 自提时间
	 */
	@Schema(description = "自提时间")
	private String pickupTime;

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
	 * 全详细地址
	 */
	@Schema(description = "全详细地址")
	private String fullAddress;

	/**
	 * 删除标识 0:未删除 1:已删除
	 */
	@Schema(description = "删除标识 0:未删除 1:已删除")
	private DeleteFlag delFlag;

	/**
	 * 自提点地区id
	 */
	@Schema(description = "自提点地区id")
	private List<String> areaIds;

	/**
	 * 批量查询-非自提点idList
	 */
	@Schema(description = "批量查询-非自提点idList")
	private List<String> notPickupPointIdList;
}