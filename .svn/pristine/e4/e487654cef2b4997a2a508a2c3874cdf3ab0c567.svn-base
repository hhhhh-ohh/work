package com.wanmi.sbc.customer.payingmemberlevel.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>付费会员等级表实体类</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@Data
@Entity
@Table(name = "paying_member_level")
public class PayingMemberLevel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 付费会员等级id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	 * 付费会员等级状态 0.开启 1.暂停
	 */
	@Column(name = "level_state")
	private Integer levelState;

	/**
	 * 付费会员等级背景类型：0.背景色 1.背景图
	 */
	@Column(name = "level_back_ground_type")
	private Integer levelBackGroundType;

	/**
	 * 付费会员等级背景详情
	 */
	@Column(name = "level_back_ground_detail")
	private String levelBackGroundDetail;

	/**
	 * 付费会员等级字体颜色
	 */
	@Column(name = "level_font_color")
	private String levelFontColor;

	/**
	 * 付费会员等级商家范围：0.自营商家 1.自定义选择
	 */
	@Column(name = "level_store_range")
	private Integer levelStoreRange;

	/**
	 * 付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置
	 */
	@Column(name = "level_discount_type")
	private Integer levelDiscountType;

	/**
	 * 付费会员等级所有商品统一设置 折扣
	 */
	@Column(name = "level_all_discount")
	private BigDecimal levelAllDiscount;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
