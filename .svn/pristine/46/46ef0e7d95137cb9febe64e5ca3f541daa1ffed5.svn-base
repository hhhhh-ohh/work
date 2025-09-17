package com.wanmi.sbc.goods.buycyclegoodsinfo.model.root;

import java.math.BigDecimal;
import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>周期购sku表实体类</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Data
@Entity
@Table(name = "buy_cycle_goods_info")
public class BuyCycleGoodsInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * skuId
	 */
	@Column(name = "goods_info_id")
	private String goodsInfoId;

	/**
	 * spuId
	 */
	@Column(name = "goods_id")
	private String goodsId;

	/**
	 * 周期购id
	 */
	@Column(name = "buy_cycle_id")
	private Long buyCycleId;

	/**
	 * 周期购商品状态 0：生效；1：失效
	 */
	@Column(name = "cycle_state")
	private Integer cycleState;

	/**
	 * 最低期数
	 */
	@Column(name = "min_cycle_num")
	private Integer minCycleNum;

	/**
	 * 每期价格
	 */
	@Column(name = "cycle_price")
	private BigDecimal cyclePrice;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
