package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>社区团购团长表VO</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Schema
@Data
public class CommunityLeaderVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	private String leaderId;

	/**
	 * 团长账号
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	@Schema(description = "团长账号")
	private String leaderAccount;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

	/**
	 * 团长名称
	 */
	@SensitiveWordsField(signType = SignWordType.NAME)
	@Schema(description = "团长名称")
	private String leaderName;

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
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 审核时间
	 */
	@Schema(description = "审核时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime checkTime;

	/**
	 * 禁用时间
	 */
	@Schema(description = "禁用时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime disableTime;

	/**
	 * 是否帮卖 0:否 1:是
	 */
	@Schema(description = "是否帮卖 0:否 1:是")
	private Integer assistFlag;

	/**
	 * 会员头像
	 */
	@Schema(description = "会员头像")
	private String headImg;

	/**
	 * 已入账佣金
	 */
	@Schema(description = "已入账佣金")
	private BigDecimal commissionReceived = BigDecimal.ZERO;

	/**
	 * 已入账自提佣金
	 */
	@Schema(description = "已入账自提佣金")
	private BigDecimal commissionReceivedPickup = BigDecimal.ZERO;

	/**
	 * 已入账帮卖佣金
	 */
	@Schema(description = "已入账帮卖佣金")
	private BigDecimal commissionReceivedAssist = BigDecimal.ZERO;

	/**
	 * 未入账佣金
	 */
	@Schema(description = "未入账佣金")
	private BigDecimal commissionPending = BigDecimal.ZERO;

	/**
	 * 未入账自提佣金
	 */
	@Schema(description = "未入账自提佣金")
	private BigDecimal commissionPendingPickup = BigDecimal.ZERO;

	/**
	 * 未入账帮卖佣金
	 */
	@Schema(description = "未入账帮卖佣金")
	private BigDecimal commissionPendingAssist = BigDecimal.ZERO;

	/**
	 * 注销状态 0:正常 1:注销中 2:已注销
	 */
	@Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
	private LogOutStatus logOutStatus;

	/**
	 * 考虑到后面可能会有很多类似“企业会员”的标签，用List存放标签内容
	 */
	@Schema(description = "会员标签")
	private List<String> customerLabelList = new ArrayList<>();

	/**
	 * 企业会员名称
	 */
	@Schema(description = "企业会员名称")
	private String enterpriseCustomerName;

	/**
	 * 企业会员logo
	 */
	@Schema(description = "企业会员logo")
	private String enterpriseCustomerLogo;
}