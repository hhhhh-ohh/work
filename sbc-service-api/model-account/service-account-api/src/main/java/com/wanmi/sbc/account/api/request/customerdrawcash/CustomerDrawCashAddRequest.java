package com.wanmi.sbc.account.api.request.customerdrawcash;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.api.request.AccountBaseRequest;
import com.wanmi.sbc.account.bean.enums.*;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>会员提现管理新增参数</p>
 * @author chenyufei
 * @date 2019-02-25 17:22:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDrawCashAddRequest extends AccountBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 提现单号(订单编号)
	 */
	@Schema(description = "提现单号(订单编号)")
	private String drawCashNo;

	/**
	 * 申请时间
	 */
	@Schema(description = "申请时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime applyTime;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	private String customerName;

	/**
	 * 会员账号
	 */
	@Schema(description = "会员账号")
	private String customerAccount;

	/**
	 * 提现渠道 0:微信 1:支付宝
	 */
	@Schema(description = "提现渠道 0:微信 1:支付宝", contentSchema = com.wanmi.sbc.account.bean.enums.DrawCashChannel.class)
	private DrawCashChannel drawCashChannel;

	/**
	 * 提现账户名称
	 */
	@NotBlank
	@Schema(description = "提现账户名称", required = true)
	private String drawCashAccountName;

	/**
	 * 提现账户账号
	 */
	@Schema(description = "提现账户账号")
	private String drawCashAccount;

	/**
	 * 本次提现金额，最小为1
	 */
	@NotNull
	@Min(1)
	@Schema(description = "本次提现金额", required = true)
	private BigDecimal drawCashSum = BigDecimal.ZERO;

	/**
	 * 提现备注
	 */
	@Schema(description = "提现备注")
	private String drawCashRemark;

	/**
	 * 运营端审核状态(0:待审核,1:审核不通过,2:审核不通过)
	 */
	@Schema(description = "运营端审核状态(0:待审核,1:审核不通过,2:审核不通过)", contentSchema = com.wanmi.sbc.account.bean.enums.AuditStatus.class)
	private AuditStatus auditStatus;

	/**
	 * 运营端驳回原因
	 */
	@Schema(description = "运营端驳回原因")
	private String rejectReason;

	/**
	 * 提现状态(0:未提现,1:提现失败,2:提现成功)
	 */
	@Schema(description = "提现状态(0:未提现,1:提现失败,2:提现成功)",
			contentSchema = com.wanmi.sbc.account.bean.enums.DrawCashStatus.class)
	private DrawCashStatus drawCashStatus;

	/**
	 * 提现失败原因
	 */
	@Schema(description = "提现失败原因")
	private String drawCashFailedReason;

	/**
	 * 用户操作状态(0:已申请,1:已取消)
	 */
	@Schema(description = "用户操作状态(0:已申请,1:已取消)",
			contentSchema = com.wanmi.sbc.account.bean.enums.CustomerOperateStatus.class)
	private CustomerOperateStatus customerOperateStatus;

	/**
	 * 提现单完成状态(0:未完成,1:已完成)
	 */
	@Schema(description = "提现单完成状态(0:未完成,1:已完成)")
	private FinishStatus finishStatus;

	/**
	 * 提现单完成时间
	 */
	@Schema(description = "提现单完成时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime finishTime;

	/**
	 * 操作人
	 */
	@Schema(description = "操作人")
	private String supplierOperateId;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标志(0:未删除,1:已删除)
	 */
	@Schema(description = "删除标志(0:未删除,1:已删除)",
			contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
	private DeleteFlag delFlag;

	/**
	 * 微信openId
	 */
	@Schema(description = "微信openId")
	private String openId;

	/**
	 * 微信openId来源 0:PC 1:MOBILE 2:App
	 */
	@Schema(description = "微信openId来源(0:PC,1:MOBILE,2:APP,3:小程序)",
			contentSchema = com.wanmi.sbc.account.bean.enums.DrawCashSource.class)
	private DrawCashSource drawCashSource;

	/**
	 * 换取access_token的票据
	 */
	@Schema(description = "换取access_token的票据")
	private String code;

	/**
	 * 提现密码
	 */
	@Schema(description = "密码", required = true)
	@NotBlank
	private String payPassword;
}