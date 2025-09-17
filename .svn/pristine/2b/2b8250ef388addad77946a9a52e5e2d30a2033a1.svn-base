package com.wanmi.sbc.order.leadertradedetail.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;

import jakarta.persistence.*;

/**
 * <p>团长订单实体类</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@Data
@Entity
@Table(name = "leader_trade_detail")
public class LeaderTradeDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 团长ID
	 */
	@Column(name = "leader_id")
	private String leaderId;

	/**
	 * 团长的会员ID
	 */
	@Column(name = "leader_customer_id")
	private String leaderCustomerId;

	/**
	 * 社区团购活动ID
	 */
	@Column(name = "community_activity_id")
	private String communityActivityId;

	/**
	 * 订单会员ID
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 会员名称
	 */
	@Column(name = "customer_name")
	private String customerName;

	/**
	 * 会员头像
	 */
	@Column(name = "customer_pic")
	private String customerPic;

	/**
	 * 订单ID
	 */
	@Column(name = "trade_id")
	private String tradeId;

	/**
	 * 商品ID
	 */
	@Column(name = "goods_info_id")
	private String goodsInfoId;

	/**
	 * 商品规格
	 */
	@Column(name = "goods_info_spec")
	private String goodsInfoSpec;

	/**
	 * 商品数量
	 */
	@Column(name = "goods_info_num")
	private Long goodsInfoNum;

	/**
	 * 跟团号
	 */
	@Column(name = "activity_trade_no")
	private Long activityTradeNo;

	/**
	 * 是否删除 
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 是否帮卖订单
	 */
	@Column(name = "bool_flag")
	@Enumerated
	private BoolFlag boolFlag;

}