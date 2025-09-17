package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.SensitiveUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>分销员VO</p>
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@Data
public class DistributionCustomerVO extends BasicResponse {

	private static final long serialVersionUID = 1L;

	/**
	 * 分销员标识UUID
	 */
    @Schema(description = "分销员标识UUID")
	private String distributionId;

	/**
	 * 会员ID
	 */
    @Schema(description = "会员ID")
	private String customerId;

	/**
	 * 会员名称
	 */
    @Schema(description = "会员名称")
	@SensitiveWordsField(signType = SignWordType.NAME)
	private String customerName;

	/**
	 * 会员登录账号|手机号
	 */
    @Schema(description = "会员登录账号|手机号")
	@SensitiveWordsField(signType = SignWordType.PHONE)
	private String customerAccount;

	/**
	 * 会员头像
	 */
	@Schema(description = "会员头像")
	private String headImg;



	/**
	 * 创建时间
	 */
    @Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人(后台新增分销员)
	 */
    @Schema(description = "创建人(后台新增分销员)")
	private String createPerson;

	/**
	 * 是否删除标志 0：否，1：是
	 */
    @Schema(description = "是否删除标志 0：否，1：是")
	private DeleteFlag delFlag;

	/**
	 * 是否禁止分销 0: 启用中  1：禁用中
	 */
    @Schema(description = "是否禁止分销 0: 启用中  1：禁用中")
	private DefaultFlag forbiddenFlag;

	/**
	 * 禁用原因
	 */
    @Schema(description = "禁用原因")
	private String forbiddenReason;

	/**
	 * 是否有分销员资格0：否，1：是
	 */
    @Schema(description = "是否有分销员资格0：否，1：是")
	private DefaultFlag distributorFlag;

	/**
	 * 邀新人数
	 */
    @Schema(description = "邀新人数")
	private Integer inviteCount;

	/**
	 * 有效邀新人数
	 */
    @Schema(description = "有效邀新人数")
	private Integer inviteAvailableCount;

	/**
	 * 邀新奖金(元)
	 */
    @Schema(description = "邀新奖金(元)")
	private BigDecimal rewardCash;

	/**
	 * 未入账邀新奖金(元)
	 */
    @Schema(description = "未入账邀新奖金(元)")
	private BigDecimal rewardCashNotRecorded;

	/**
	 * 分销订单(笔)
	 */
    @Schema(description = "分销订单(笔)")
	private Integer distributionTradeCount;



	/**
	 * 销售额(元) 
	 */
    @Schema(description = "销售额(元) ")
	private BigDecimal sales;

	/**
	 * 分销佣金(元) 
	 */
    @Schema(description = "分销佣金(元) ")
	private BigDecimal commission;

	/**
	 * 未入账分销佣金(元) 
	 */
    @Schema(description = "未入账分销佣金(元) ")
	private BigDecimal commissionNotRecorded;

	/**
	 * 佣金总额(元)
	 */
	@Schema(description = "佣金总额(元) ")
	private BigDecimal commissionTotal;

	/**
	 * 分销员等级ID
	 */
	@Schema(description = "分销员等级ID ")
	private String distributorLevelId;

	/**
	 * 分销员等级名称
	 */
	@Schema(description = "分销员等级名称 ")
	private String distributorLevelName;

	/**
	 * 邀请码
	 */
	@Schema(description = "邀请码 ")
	private String inviteCode;

	/**
	 * 邀请人会员ID集合，后期可扩展N级
	 */
	@Schema(description = "邀请人会员ID集合，后期可扩展N级")
	private String inviteCustomerIds;

	/**
	 * 注销状态 0:正常 1:注销中 2:已注销
	 */
	@Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
	private LogOutStatus logOutStatus;

	/**
	 * 会员登录账号|手机号-脱敏
	 */
	public String getCustomerAccount() {
		return SensitiveUtils.handlerMobilePhone(customerAccount);
	}

	/**
	 * 会员名称-脱敏
	 */
	public String getCustomerName() {
		return SensitiveUtils.handlerMobilePhone(customerName);
	}
}