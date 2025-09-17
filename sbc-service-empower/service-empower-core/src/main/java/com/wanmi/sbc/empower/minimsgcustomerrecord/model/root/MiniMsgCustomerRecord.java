package com.wanmi.sbc.empower.minimsgcustomerrecord.model.root;


import com.wanmi.sbc.common.enums.TriggerNodeType;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * <p>客户订阅消息信息表实体类</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@Data
@Entity
@Table(name = "mini_program_subscribe_message_customer_record")
public class MiniMsgCustomerRecord {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 推送活动id
	 */
	@Column(name = "message_activity_id")
	private Long messageActivityId;

	/**
	 * 模版主键ID
	 */
	@Column(name = "template_setting_id")
	private Long templateSettingId;

	/**
	 * 触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时
	 */
	@Column(name = "trigger_node_id")
	private TriggerNodeType triggerNodeId;

	/**
	 * 第三方用户id
	 */
	@Column(name = "open_id")
	private String openId;

	/**
	 * 会员Id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 推送结果状态码
	 */
	@Column(name = "err_code")
	private String errCode;

	/**
	 * 推送结果描述
	 */
	@Column(name = "err_msg")
	private String errMsg;

	/**
	 * 是否推送标志 0：否，1：是
	 */
	@Column(name = "send_flag")
	private Integer sendFlag;

}
