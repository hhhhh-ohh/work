package com.wanmi.sbc.message.api.request.minimsgtempsetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.TriggerNodeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>单个查询小程序订阅消息模版配置表请求参数</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgTempSettingByNodeIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时
	 */
	@Schema(description = "触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时")
	private TriggerNodeType triggerNodeId;

}