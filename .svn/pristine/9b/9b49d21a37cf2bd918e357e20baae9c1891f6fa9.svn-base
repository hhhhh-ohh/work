package com.wanmi.sbc.empower.bean.vo;

import com.wanmi.sbc.common.enums.TriggerNodeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>客户订阅消息信息表VO</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@Schema
@Data
public class MiniMsgCustomerRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 推送活动id
	 */
	@Schema(description = "推送活动id")
	private Long messageActivityId;

	/**
	 * 模版主键ID
	 */
	@Schema(description = "模版主键ID")
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
	private String openId;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	private String customerId;

	/**
	 * 推送结果状态码
	 */
	@Schema(description = "推送结果状态码")
	private String errCode;

	/**
	 * 推送结果描述
	 */
	@Schema(description = "推送结果描述")
	private String errMsg;

	/**
	 * 是否推送标志 0：否，1：是
	 */
	@Schema(description = "是否推送标志 0：否，1：是")
	private Integer sendFlag;

}