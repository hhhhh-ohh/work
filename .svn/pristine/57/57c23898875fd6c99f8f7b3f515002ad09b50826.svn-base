package com.wanmi.sbc.marketing.newcomerpurchaseconfig.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>新人专享设置实体类</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@Data
@Entity
@Table(name = "newcomer_purchase_config")
public class NewcomerPurchaseConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 海报设置
	 */
	@Column(name = "poster")
	private String poster;

	/**
	 * 优惠券样式布局
	 */
	@Column(name = "coupon_layout")
	private Integer couponLayout;

	/**
	 * 商品样式布局
	 */
	@Column(name = "goods_layout")
	private Integer goodsLayout;

	/**
	 * 活动规则详细
	 */
	@Column(name = "rule_detail")
	private String ruleDetail;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
