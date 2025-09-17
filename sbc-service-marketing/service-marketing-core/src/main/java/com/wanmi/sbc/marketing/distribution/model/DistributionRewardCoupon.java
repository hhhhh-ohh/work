package com.wanmi.sbc.marketing.distribution.model;


import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>奖励优惠券实体类</p>
 * @author gaomuwei
 * @date 2019-02-19 10:40:20
 */
@Data
@Entity
@Table(name = "distribution_reward_coupon")
public class DistributionRewardCoupon implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "reward_coupon_id")
	private String rewardCouponId;

	/**
	 * 优惠券id
	 */
	@Column(name = "coupon_id")
	private String couponId;

	/**
	 * 每组张数
	 */
	@Column(name = "count")
	private Integer count;

}