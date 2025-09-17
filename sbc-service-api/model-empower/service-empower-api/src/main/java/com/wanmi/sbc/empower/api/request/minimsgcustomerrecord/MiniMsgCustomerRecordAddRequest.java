package com.wanmi.sbc.empower.api.request.minimsgcustomerrecord;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.TriggerNodeType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

/**
 * <p>客户订阅消息信息表新增参数</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgCustomerRecordAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 推送活动id
	 */
	@Schema(description = "推送活动id")
	@Max(9223372036854775807L)
	private Long messageActivityId;

	/**
	 * 模版主键ID
	 */
	@Schema(description = "模版主键ID")
	@Max(9223372036854775807L)
	private Long templateSettingId;

	/**
	 * 触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时
	 */
	@Schema(description = "触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时")
	private TriggerNodeType triggerNodeId;

	/**
	 * 第三方用户id
	 */
	@Schema(description = "第三方用户id")
	@NotBlank
	@Length(max=36)
	private String openId;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	@Length(max=32)
	private String customerId;

	/**
	 * 推送结果状态码
	 */
	@Schema(description = "推送结果状态码")
	@Length(max=50)
	private String errCode;

	/**
	 * 推送结果描述
	 */
	@Schema(description = "推送结果描述")
	@Length(max=256)
	private String errMsg;

	/**
	 * 是否推送标志 0：否，1：是
	 */
	@Schema(description = "是否推送标志 0：否，1：是")
	@NotNull
	@Max(127)
	private Integer sendFlag;

}