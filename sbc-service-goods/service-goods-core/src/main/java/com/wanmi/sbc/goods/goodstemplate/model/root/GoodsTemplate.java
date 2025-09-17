package com.wanmi.sbc.goods.goodstemplate.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>GoodsTemplate实体类</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Data
@Entity
@Table(name = "goods_template")
public class GoodsTemplate extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 展示位置 0:顶部 1:底部 2:全选
	 */
	@Column(name = "position")
	private Integer position;

	/**
	 * 顶部内容
	 */
	@Column(name = "top_content")
	private String topContent;

	/**
	 * 底部内容
	 */
	@Column(name = "down_content")
	private String downContent;

	/**
	 * 店铺Id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
