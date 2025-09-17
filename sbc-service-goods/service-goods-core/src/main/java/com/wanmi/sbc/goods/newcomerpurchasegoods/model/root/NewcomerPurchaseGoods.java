package com.wanmi.sbc.goods.newcomerpurchasegoods.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>新人购商品表实体类</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@Data
@Entity
@Table(name = "newcomer_purchase_goods")
public class NewcomerPurchaseGoods extends BaseEntity {


	private static final long serialVersionUID = 9191188387595500223L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * sku ID
	 */
	@Column(name = "goods_info_id")
	private String goodsInfoId;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
