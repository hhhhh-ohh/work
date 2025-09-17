package com.wanmi.sbc.goods.goodstemplate.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>GoodsTemplateRel实体类</p>
 * @author 黄昭
 * @date 2022-10-09 10:59:41
 */
@Data
@Entity
@Table(name = "goods_template_rel")
public class GoodsTemplateRel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 模版id
	 */
	@Column(name = "template_id")
	private Long templateId;

	/**
	 * 商品id
	 */
	@Column(name = "goods_id")
	private String goodsId;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
