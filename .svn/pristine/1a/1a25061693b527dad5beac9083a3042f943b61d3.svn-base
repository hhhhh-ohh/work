package com.wanmi.sbc.marketing.newcomerpurchasecoupon.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>新人购优惠券实体类</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@Data
@Entity
@Table(name = "newcomer_purchase_coupon")
public class NewcomerPurchaseCoupon extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 优惠券id
	 */
	@Column(name = "coupon_id")
	private String couponId;


	/**
	 * 优惠券名称
	 */
	@Column(name = "coupon_name")
	private String couponName;

	/**
	 * 券组库存
	 */
	@Column(name = "coupon_stock")
	private Long couponStock;

	/**
	 * 活动原始库存
	 */
	@Column(name = "activity_stock")
	private Long activityStock;


	/**
	 * 每组赠送数量
	 */
	@Column(name = "group_of_num")
	private Integer groupOfNum;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
