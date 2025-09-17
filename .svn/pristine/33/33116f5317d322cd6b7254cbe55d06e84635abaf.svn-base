package com.wanmi.sbc.goods.buycyclegoods.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import lombok.Data;

/**
 * <p>周期购spu表实体类</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Data
@Entity
@Table(name = "buy_cycle_goods")
public class BuyCycleGoods extends BaseEntity {


	private static final long serialVersionUID = -3877782453802176812L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * spuId
	 */
	@Column(name = "goods_id")
	private String goodsId;

	/**
	 * 配送周期
	 */
	@Column(name = "delivery_cycle")
	private Integer deliveryCycle;

	/**
	 * 自选期数
	 */
	@Column(name = "optional_num")
	private Integer optionalNum;

	/**
	 * 用户可选送达日期
	 */
	@Column(name = "delivery_date")
	private String deliveryDate;

	/**
	 * 预留时间 日期
	 */
	@Column(name = "reserve_day")
	private Integer reserveDay;

	/**
	 * 预留时间 时间点
	 */
	@Column(name = "reserve_time")
	private Integer reserveTime;



	/**
	 * 周期购商品状态 0：生效；1：失效
	 */
	@Column(name = "cycle_state")
	private Integer cycleState;


	/**
	 * 店铺Id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;


	/**
	 * 商品名称
	 */
	@Column(name = "goods_name")
	private String goodsName;
}
