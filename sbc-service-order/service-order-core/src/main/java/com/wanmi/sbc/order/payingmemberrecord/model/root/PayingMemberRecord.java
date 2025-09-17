package com.wanmi.sbc.order.payingmemberrecord.model.root;

import java.math.BigDecimal;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>付费记录表实体类</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@Data
@Entity
@Table(name = "paying_member_record")
public class PayingMemberRecord extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录id
	 */
	@Id
	@Column(name = "record_id")
	private String recordId;

	/**
	 * 付费会员等级id
	 */
	@Column(name = "level_id")
	private Integer levelId;

	/**
	 * 付费会员等级名称
	 */
	@Column(name = "level_name")
	private String levelName;

	/**
	 * 付费会员等级昵称
	 */
	@Column(name = "level_nick_name")
	private String levelNickName;

	/**
	 * 会员id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 会员名称
	 */
	@Column(name = "customer_name")
	private String customerName;

	/**
	 * 支付数量
	 */
	@Column(name = "pay_num")
	private Integer payNum;

	/**
	 * 支付金额
	 */
	@Column(name = "pay_amount")
	private BigDecimal payAmount;

	/**
	 * 支付时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "pay_time")
	private LocalDateTime payTime;

	/**
	 * 会员到期时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	/**
	 * 等级状态：0.生效中，1.未生效，2.已过期，3.已退款
	 */
	@Column(name = "level_state")
	private Integer levelState;

	/**
	 * 已优惠金额
	 */
	@Column(name = "already_discount_amount")
	private BigDecimal alreadyDiscountAmount;

	/**
	 * 已领积分
	 */
	@Column(name = "already_receive_point")
	private Long alreadyReceivePoint;

	/**
	 * 退款金额
	 */
	@Column(name = "return_amount")
	private BigDecimal returnAmount;


	/**
	 * 退款原因
	 */
	@Column(name = "return_cause")
	private String returnCause;

	/**
	 * 退款回收券 0.是，1.否
	 */
	@Column(name = "return_coupon")
	private Integer returnCoupon;

	/**
	 * 退款回收积分 0.是，1.否
	 */
	@Column(name = "return_point")
	private Integer returnPoint;

	/**
	 * 权益id集合
	 */
	@Column(name = "rights_ids")
	private String rightsIds;

	/**
	 * 首次开通 0.是，1.否
	 */
	@Column(name = "first_open")
	private Integer firstOpen;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
