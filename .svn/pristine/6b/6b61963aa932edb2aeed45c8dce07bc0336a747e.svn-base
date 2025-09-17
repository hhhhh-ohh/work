package com.wanmi.sbc.message.minimsgrecord.model.root;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * <p>小程序订阅消息配置表实体类</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@Data
@Entity
@Table(name = "mini_program_subscribe_authorization_record")
public class MiniMsgRecord {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 会员Id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 订单支付成功次数
	 */
	@Column(name = "order_pay_success_num")
	private Integer orderPaySuccessNum;

	/**
	 * 退单提交成功次数
	 */
	@Column(name = "refund_order_success_num")
	private Integer refundOrderSuccessNum;

	/**
	 * 查看我的优惠券次数
	 */
	@Column(name = "view_coupon_num")
	private Integer viewCouponNum;

	/**
	 * 参与/发起拼团成功次数
	 */
	@Column(name = "groupon_success_num")
	private Integer grouponSuccessNum;

	/**
	 * 商品预约成功次数
	 */
	@Column(name = "appointment_success_num")
	private Integer appointmentSuccessNum;

	/**
	 * 付费会员购买成功次数
	 */
	@Column(name = "member_pay_success_num")
	private Integer memberPaySuccessNum;

}
